package com.ns.ejk.form.dao.es;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lenovo on 2018/2/9.
 */
@Repository
public class EsRestDao extends AbsEsRestDao<Map> {
    @Value("${spring.data.elasticsearch.form-index-name}")
    private String indexName;

    @Override
    protected String getIndexId(Map map) {
        return map.get("id").toString();
    }

    @Override
    protected Class<Map> getClazz() {
        return Map.class;
    }


}
