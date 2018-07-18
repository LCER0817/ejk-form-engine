package com.ns.ejk.form.util.checker;

import com.ns.common.util.checker.BeanChecker;
import com.ns.ejk.form.bean.SchemaItem;
import org.springframework.stereotype.Component;

@Component
public class SchemaItemChecker extends BeanChecker {

    public void checkCreate(SchemaItem item) {
        assertNotNull(item, "表单schema项不能为空");
        assertNotNull(item.getSchemaId(), "表单项schemaId不能为空");
        assertNotEmpty(item.getFieldName(), "表单项属性名不能为空");
        assertNotEmpty(item.getDisplayName(), "表单项展示属性名不能为空");
    }
}
