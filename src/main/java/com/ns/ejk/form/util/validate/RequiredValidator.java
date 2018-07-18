package com.ns.ejk.form.util.validate;

import com.ns.ejk.form.util.errorcode.FormEngineError;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by liqiuwei on 2018/1/4.
 */
@Component
public class RequiredValidator extends AbsValidator {

    /**
     * 必填验证
     */
    @Override
    protected String processValidate(String dataValue, String schemaValue) {
        if (StringUtils.isBlank(dataValue)) {
            return FormEngineError.NOT_NUll.getMsg();
        }
        return null;
    }
}
