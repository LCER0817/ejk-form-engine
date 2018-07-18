package com.ns.ejk.form.action.doctor;

import com.ns.common.util.constant.PathConstant;
import com.ns.ejk.form.bean.FormData;
import com.ns.ejk.form.bean.act.FormDataReq;
import com.ns.ejk.form.biz.FormDataBiz;
import com.ns.ejk.form.biz.act.FormDataActBiz;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(PathConstant.DOCTOR + "/formData")
public class FormDataAction {
    @Resource
    private FormDataActBiz actBiz;
    @Resource
    private FormDataBiz biz;

    @PostMapping("/getOne")
    public Object getOne(@RequestBody FormDataReq req) {
        return biz.getData(req.getSchemaName(), req.getEsSearch());
    }

    @PostMapping("/search")
    public Object search(@RequestBody FormDataReq req) {
        return biz.search(req.getSchemaName(), req.getEsSearch(), req.getEjkPage());
    }

    @PostMapping("/create")
    public Object create(@RequestBody FormData formData) {
        return actBiz.create(formData);
    }

    @PostMapping("/modify")
    public Object modify(@RequestBody FormData formData) {
        return actBiz.modify(formData);
    }
}
