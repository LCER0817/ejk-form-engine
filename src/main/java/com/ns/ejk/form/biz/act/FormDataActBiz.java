package com.ns.ejk.form.biz.act;

import com.ns.ejk.form.bean.FormData;
import com.ns.ejk.form.biz.FormDataBiz;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class FormDataActBiz {
    @Resource
    private FormDataBiz biz;

    public Map<String, Object> create(FormData formData) {
        return biz.create(formData).getDataItemMap();
    }

    public Map<String, Object> modify(FormData formData) {
        return biz.modify(formData).getDataItemMap();
    }
}
