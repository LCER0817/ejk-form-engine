package com.ns.ejk.form.util.validate;

import com.ns.ejk.form.util.errorcode.FormEngineError;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by liqiuwei on 2018/1/4.
 */
@Component
public class RegexValidator extends AbsValidator {


    @Override
    protected String processValidate(String dataValue, String schemaValue) {
        if (!Pattern.matches(schemaValue, dataValue)) {
            return FormEngineError.DATA_NOT_MATCH.getMsg();
        }
        return null;
    }
}
