package com.ns.ejk.form.dao.es;

import com.ns.common.bean.EjkPage;
import com.ns.common.util.bean.PageUtil;
import com.ns.common.util.exception.errorcode.CommonErrorCode;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;
import com.ns.common.util.log.LoggerUtil;
import com.ns.ejk.form.bean.es.EsSearch;
import com.ns.ejk.form.bean.es.EsSort;
import com.ns.ejk.form.util.bean.es.EsSearchUtil;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


/**
 * Created by lenovo on 2018/2/9.
 */


public abstract class AbsEsRestDao<T> {
    private Logger logger = LoggerUtil.getLogger(AbsEsRestDao.class);
    @Resource
    private JestClient jestClient;


    public T findOne(String indexName, String type, String id) {
        Get get = new Get.Builder(indexName, id).type(type).build();
        JestResult result = execute(get);
        return result.getSourceAsObject(getClazz());
    }


    public Page search(String indexName, String type, EjkPage ejkPage, EsSearch esSearch) {
        Search.Builder builder = new Search.Builder(EsSearchUtil.getQueryString(esSearch));
        builder.addIndex(indexName).addType(type)
                .setParameter("from", ejkPage.getOffset())
                .setParameter("size", ejkPage.getPagesize());

        List<EsSort> sorts = esSearch.getSorts();
        if (CollectionUtils.isNotEmpty(sorts)) {
            for (Sort sort : sorts) {
                builder.addSort(sort);
            }
        }

        JestResult result = execute(builder.build());
        int count = result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
        List<T> list = result.getSourceAsObjectList(getClazz());
        Pageable pageable = PageUtil.createPageable(ejkPage);
        return new PageImpl(list, pageable, count);
    }


    public void save(String indexName, String type, T obj) {
        Index index = new Index.Builder(obj).index(indexName).type(type).id(getIndexId(obj)).build();
        execute(index);
    }

    public void delete(String indexName, String type, String id) {
        Delete delete = new Delete.Builder(id).index(indexName).type(type).build();
        execute(delete);
    }

    private JestResult execute(Action action) {
        JestResult jestResult = null;
        try {
            jestResult = jestClient.execute(action);
            if (jestResult != null && !jestResult.isSucceeded()) {
                logger.error(String.format("error msg is:%s, json:%s", jestResult.getErrorMessage(), jestResult.getJsonString()));
                throw new NSException(CommonErrorCode.OPERATIOIN_FAIL);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new SystemInternalException();
        }
        return jestResult;
    }


    protected abstract String getIndexId(T obj);


    protected abstract Class<T> getClazz();


}
