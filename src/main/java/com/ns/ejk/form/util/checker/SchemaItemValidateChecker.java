package com.ns.ejk.form.util.checker;

import com.ns.common.util.checker.BeanChecker;
import com.ns.ejk.form.bean.SchemaItemValidate;
import org.springframework.stereotype.Component;

@Component
public class SchemaItemValidateChecker extends BeanChecker {

    public void checkCreate(SchemaItemValidate validate) {
        assertNotNull(validate, "表单校验信息不能为空");
        assertNotNull(validate.getSchemaItemId(), "表单校验schemaId不能为空");
        assertNotEmpty(validate.getType(), "表单校验类型不能为空");
    }
}
