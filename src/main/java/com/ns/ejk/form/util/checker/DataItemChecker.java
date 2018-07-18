package com.ns.ejk.form.util.checker;

import com.ns.common.util.checker.BeanChecker;
import com.ns.ejk.form.bean.DataItem;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataItemChecker extends BeanChecker {

    public void checkCreate(DataItem item) {
        assertNotNull(item, "表单数据项不能为空");
        assertNotEmpty(item.getFieldName(), "表单属性名不能为空");
        assertNotEmpty(item.getValue(), "表单属性值不能为空");
    }

    public void checkCreateBatch(Set<DataItem> dataItems) {
        for (DataItem item : dataItems) {
            checkCreate(item);
        }
    }
}
