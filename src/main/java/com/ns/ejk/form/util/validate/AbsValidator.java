package com.ns.ejk.form.util.validate;

import com.ns.common.util.exception.sys.NSException;

/**
 * Created by liqiuwei on 2018/1/4.
 */
public abstract class AbsValidator implements Validator {


    public String validate(String dataValue, String schemaValue) throws NSException {
        if (dataValue == null) {
            return null;
        }
        return processValidate(dataValue, schemaValue);
    }

    protected abstract String processValidate(String dataValue, String schemaValue);
}
