package com.ns.ejk.form.mgr;

import com.ns.common.bean.EjkPage;
import com.ns.common.util.datetime.DateTimeUtil;
import com.ns.ejk.form.bean.FormData;
import com.ns.ejk.form.bean.FormSchema;
import com.ns.ejk.form.bean.es.EsSearch;
import com.ns.ejk.form.dao.FormDataDao;
import com.ns.ejk.form.dao.FormSchemaDao;
import com.ns.ejk.form.dao.es.EsRestDao;
import com.ns.ejk.form.util.constant.FormDataConstant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FormDataMgr {
    @Resource
    private FormDataDao dao;
    @Resource
    private EsRestDao esDao;
    @Resource
    private EntityManager em;
    @Resource
    private FormSchemaDao formSchemaDao;

    private static final Map<String, String> schemaMap = new HashMap<>();

    @PostConstruct
    private void init() {
        List<FormSchema> schemaList = formSchemaDao.findAll();
        Map<String, String> map = schemaList.stream().collect(Collectors.toMap(o -> o.getName(), o -> o.getIdxName()));
        schemaMap.putAll(map);
    }

    public FormData getById(long id) {
        return dao.findOne(id);
    }

    public FormData getAndLock(long id) {
        return dao.getAndLock(em, FormData.class, id);
    }

    public Page search(String schemaName, EsSearch esSearch, EjkPage ejkPage) {
        return esDao.search(schemaMap.get(schemaName), schemaName, ejkPage, esSearch);
    }

    public FormData create(FormData formData) {
        formData.setCreateTime(DateTimeUtil.getNowDate());
        formData = dao.save(formData);

        Map<String, Object> dataItemMap = formData.getDataItemMap();
        dataItemMap.put(FormDataConstant.ID, formData.getId());
        dataItemMap.put(FormDataConstant.CREATE_TIME, formData.getCreateTime());
        dataItemMap.put(FormDataConstant.UPDATE_TIME, formData.getUpdateTime());
        dataItemMap.put(FormDataConstant.SCHEMA_NAME, formData.getSchemaName());
        dataItemMap.put(FormDataConstant.SCHEMA_ID, formData.getSchemaId());
        esDao.save(schemaMap.get(formData.getSchemaName()), formData.getSchemaName(), dataItemMap);
        return formData;
    }

    public void modify(FormData formData) {
        formData = dao.save(formData);

        Map<String, Object> dataItemMap = formData.getDataItemMap();
        dataItemMap.put(FormDataConstant.ID, formData.getId());
        dataItemMap.put(FormDataConstant.CREATE_TIME, formData.getCreateTime());
        dataItemMap.put(FormDataConstant.UPDATE_TIME, formData.getUpdateTime());
        dataItemMap.put(FormDataConstant.SCHEMA_NAME, formData.getSchemaName());
        dataItemMap.put(FormDataConstant.SCHEMA_ID, formData.getSchemaId());
        esDao.save(schemaMap.get(formData.getSchemaName()), formData.getSchemaName(), dataItemMap);
    }

    public void deleteById(long id) {
        FormData o = dao.findOne(id);
        FormSchema formSchema = formSchemaDao.findOne(o.getSchemaId());
        o.setSchemaName(formSchema.getName());

        dao.delete(id);
        esDelete(o);
    }

    public void esDelete(FormData formData) {
        esDao.delete(schemaMap.get(formData.getSchemaName()), formData.getSchemaName(), String.valueOf(formData.getId()));
    }
}
