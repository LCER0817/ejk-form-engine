package com.ns.ejk.form.biz;

import com.ns.ejk.form.bean.SchemaItemValidate;
import com.ns.ejk.form.dao.SchemaItemValidateDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;


@Service
public class FormSchemaValidateBiz {

    @Resource
    private SchemaItemValidateDao dao;

    @Resource
    private EntityManager em;

    public SchemaItemValidate getById(long id) {
        return dao.findOne(id);
    }

    public SchemaItemValidate getAndLock(long id) {
        return dao.getAndLock(em, SchemaItemValidate.class, id);
    }

    public List<SchemaItemValidate> getBySchemaItemId(long schemaItemId) {
        return dao.getBySchemaItemId(schemaItemId);
    }

    public List<SchemaItemValidate> createBatch(List<SchemaItemValidate> validates) {
        return dao.saves(validates);
    }
}
