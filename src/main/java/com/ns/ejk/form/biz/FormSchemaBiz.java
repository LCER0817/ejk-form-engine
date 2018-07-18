package com.ns.ejk.form.biz;

import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.gson.GsonUtil;
import com.ns.ejk.form.bean.FormSchema;
import com.ns.ejk.form.bean.SchemaItem;
import com.ns.ejk.form.bean.SchemaItemValidate;
import com.ns.ejk.form.dao.FormSchemaDao;
import com.ns.ejk.form.util.DesignerUtils;
import com.ns.ejk.form.util.checker.FormSchemaChecker;
import com.ns.ejk.form.util.converter.ConvertorManager;
import com.ns.ejk.form.util.errorcode.FormEngineError;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FormSchemaBiz {
    @Resource
    private FormSchemaDao dao;
    @Resource
    private EntityManager em;
    @Resource
    private FormSchemaChecker checker;
    @Resource
    private SchemaItemBiz itemBiz;
    @Resource
    private FormSchemaValidateBiz validateBiz;

    public FormSchema getById(long id) {
        FormSchema o = dao.findOne(id);
        if (o == null) {
            throw new NSException(FormEngineError.SCHEMA_NOT_EXIST);
        }
        return o;
    }

    public FormSchema getAndLock(long id) {
        FormSchema o = dao.getAndLock(em, FormSchema.class, id);
        if (o == null) {
            throw new NSException(FormEngineError.SCHEMA_NOT_EXIST);
        }
        return o;
    }

    public FormSchema getByName(String name) {
        FormSchema o = dao.findByName(name);
        if (o == null) {
            throw new NSException(FormEngineError.SCHEMA_NOT_EXIST);
        }
        return o;
    }

    public void create(long schemaId, FormSchema formSchema) {
        checker.checkCreate(formSchema);
        Map<SchemaItem, List<SchemaItemValidate>> schemaInfo = ConvertorManager.getConvertor(schemaId).convertSchema(formSchema);

        FormSchema schema = new FormSchema();
        schema.setId(schemaId);
        schema.setDesignerId(DesignerUtils.getDesignerId(formSchema.getClass()));
        schema.setValue(GsonUtil.toJson(formSchema));
        dao.save(schema);

        Set<Map.Entry<SchemaItem, List<SchemaItemValidate>>> entrySet = schemaInfo.entrySet();
        for (Map.Entry<SchemaItem, List<SchemaItemValidate>> entry : entrySet) {
            SchemaItem item = entry.getKey();
            List<SchemaItemValidate> validates = entry.getValue();

            item = itemBiz.create(item);
            long schemaItemId = item.getId();
            if (CollectionUtils.isNotEmpty(validates)) {
                validates.stream().forEach(validate -> {
                    validate.setSchemaItemId(schemaItemId);
                });
                validateBiz.createBatch(validates);
            }
        }

    }
}
