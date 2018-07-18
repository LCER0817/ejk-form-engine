package com.ns.ejk.form.biz;

import com.ns.ejk.form.bean.SchemaItem;
import com.ns.ejk.form.bean.SchemaItemValidate;
import com.ns.ejk.form.dao.SchemaItemDao;
import com.ns.ejk.form.dao.SchemaItemValidateDao;
import com.ns.ejk.form.util.checker.SchemaItemChecker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchemaItemBiz {

    @Resource
    private EntityManager em;

    @Resource
    private SchemaItemDao dao;

    @Resource
    private SchemaItemValidateDao validateDao;

    @Resource
    private SchemaItemChecker checker;


    public SchemaItem getById(long id) {
        return dao.findOne(id);
    }

    public SchemaItem getAndLock(long id) {
        return dao.getAndLock(em, SchemaItem.class, id);
    }

    public List<SchemaItem> getBySchemaId(long schemaId) {
        return dao.getBySchemaId(schemaId);
    }

    public Map<SchemaItem, List<SchemaItemValidate>> getAllBySchemaId(long schemaId) {

        return getBySchemaId(schemaId).stream().collect(Collectors.toMap(item -> item, item -> validateDao.getBySchemaItemId(item.getId())));
    }

    ;

    public SchemaItem create(SchemaItem item) {

        checker.checkCreate(item);
        return dao.save(item);
    }

    ;
}
