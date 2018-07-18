package com.ns.ejk.form.util.checker;

import com.ns.common.util.checker.BeanChecker;
import com.ns.ejk.form.bean.FormData;
import org.springframework.stereotype.Component;

@Component
public class FormDataChecker extends BeanChecker {

    public void checkCreate(FormData data) {
        assertNotNull(data, "表单数据不能为空");
        assertNotNull(data.getSchemaName(), "schemaName不能为空");
        assertNotEmpty(data.getDataItemMap(), "表单数据不能为空");
    }

    public void checkModify(FormData data) {
        assertNotNull(data, "表单数据不能为空");
        assertNotNull(data.getId(), "id不能为空");
        assertNotEmpty(data.getDataItemMap(), "表单数据不能为空");
    }
}
