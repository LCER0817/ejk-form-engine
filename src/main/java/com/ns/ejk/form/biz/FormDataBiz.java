package com.ns.ejk.form.biz;

import com.google.gson.reflect.TypeToken;
import com.ns.common.bean.EjkPage;
import com.ns.common.util.bean.PageUtil;
import com.ns.common.util.datetime.DateTimeUtil;
import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.SystemInternalException;
import com.ns.common.util.gson.GsonUtil;
import com.ns.common.util.log.LoggerUtil;
import com.ns.ejk.form.bean.DataItem;
import com.ns.ejk.form.bean.FormData;
import com.ns.ejk.form.bean.FormSchema;
import com.ns.ejk.form.bean.SchemaItem;
import com.ns.ejk.form.bean.es.EsSearch;
import com.ns.ejk.form.mgr.FormDataMgr;
import com.ns.ejk.form.util.checker.FormDataChecker;
import com.ns.ejk.form.util.errorcode.FormEngineError;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class FormDataBiz {
    private static final Logger logger = LoggerUtil.getLogger(FormDataBiz.class);
    @Resource
    private FormDataMgr mgr;
    @Resource
    private FormDataChecker checker;
    @Resource
    private DataItemBiz dataItemBiz;
    @Resource
    private FormSchemaBiz formSchemaBiz;
    @Resource
    private SchemaItemBiz schemaItemBiz;
    @Resource
    private ValidatorBiz validatorBiz;


    public FormData getById(long id) {
        FormData o = mgr.getById(id);
        if (o == null) {
            throw new NSException(FormEngineError.DATA_NOT_EXIST);
        }
        return o;
    }

    public FormData getAndLock(long id) {
        FormData o = mgr.getAndLock(id);
        if (o == null) {
            throw new NSException(FormEngineError.DATA_NOT_EXIST);
        }
        return o;
    }

    public Map<String, Object> getData(String schemaName, EsSearch esSearch) {
        Page page = mgr.search(schemaName, esSearch, new EjkPage());
        if (page.getTotalElements() == 0) {
            return null;
        }
        Object obj = page.getContent().iterator().next();

        return GsonUtil.fromJson(GsonUtil.toJson(obj), new TypeToken<Map<String, Object>>() {
        });
    }

    public Map<String, Object> search(String schemaName, EsSearch esSearch, EjkPage ejkPage) {
        return PageUtil.getPageResult(mgr.search(schemaName, esSearch, ejkPage));
    }

    public FormData create(FormData formData) {
        checker.checkCreate(formData);

        FormSchema schema = formSchemaBiz.getByName(formData.getSchemaName());

        fillData(formData, schema);

        validatorBiz.validateDataItems(formData);

        mgr.create(formData);

        for (DataItem item : formData.getDataItems()) {
            item.setFormDataId(formData.getId());
        }
        try {
            dataItemBiz.createBatch(formData.getDataItems());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            mgr.esDelete(formData);
            throw e;
        }

        return formData;
    }


    public FormData modify(FormData formData) {
        checker.checkModify(formData);

        FormData o = getById(formData.getId());

        FormSchema schema = formSchemaBiz.getById(o.getSchemaId());

        Map<String, Object> dataItemMap = GsonUtil.fromJson(o.getValue(), new TypeToken<Map<String, Object>>() {
        });
        dataItemMap.putAll(formData.getDataItemMap());
        o.setDataItemMap(dataItemMap);

        fillData(o, schema);

        for (DataItem item : o.getDataItems()) {
            item.setFormDataId(o.getId());
        }
        validatorBiz.validateDataItems(o);
        dataItemBiz.deleteByDataId(o.getId());
        dataItemBiz.createBatch(o.getDataItems());

        mgr.modify(o);

        return o;
    }

    public void deleteById(long id) {
        dataItemBiz.deleteByDataId(id);
        mgr.deleteById(id);
    }

    private void fillData(FormData formData, FormSchema schema) {
        if (formData == null) {
            throw new SystemInternalException();
        }
        if (schema != null) {
            formData.setSchemaId(schema.getId());
            formData.setSchemaName(schema.getName());
        }

        List<SchemaItem> schemaItems = schemaItemBiz.getBySchemaId(formData.getSchemaId());

        Map<String, Object> dataItemMap = formData.getDataItemMap();
        Set<DataItem> dataItems = new HashSet<>(dataItemMap.size());
        Map<String, Object> newDataItemMap = new HashMap<>(dataItemMap.size());

        for (SchemaItem item : schemaItems) {
            DataItem dataItem = new DataItem();
            dataItem.setFieldName(item.getFieldName());
            Object value = dataItemMap.get(item.getFieldName());
            if (value == null) {
                continue;
            }
            dataItem.setValue(GsonUtil.toJson(value));
            dataItem.setCreateTime(DateTimeUtil.getNowDate());
            dataItems.add(dataItem);

            newDataItemMap.put(item.getFieldName(), dataItemMap.get(item.getFieldName()));
        }

        formData.setDataItems(dataItems);
        formData.setDataItemMap(newDataItemMap);
        formData.setValue(GsonUtil.toJson(formData.getDataItemMap()));
    }


}
