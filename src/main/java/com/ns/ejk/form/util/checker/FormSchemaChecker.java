package com.ns.ejk.form.util.checker;

import com.ns.common.util.checker.BeanChecker;
import com.ns.ejk.form.bean.FormSchema;
import org.springframework.stereotype.Component;

@Component
public class FormSchemaChecker extends BeanChecker {

    public void checkCreate(FormSchema schema) {
        assertNotNull(schema, "表单schema信息不能为空");
        assertNotNull(schema.getId(), "表单schemaId不能为空");
        assertNotNull(schema.getDesignerId(), "表单designerId不能为空");
        assertNotEmpty(schema.getValue(), "表单schema不能为空");
    }
}
