package com.ns.ejk.form.util.validate;

import com.ns.ejk.form.util.errorcode.FormEngineError;

/**
 * Created by lenovo on 2018/1/4.
 */
public abstract class AbsRangeValidator<T> extends AbsValidator {

    protected String processValidate(String dataValue, String schemaValue) {
        if (!schemaValue.contains(",")) {
            return FormEngineError.NOT_EXITS_COMMA.getMsg();
        }
        String[] ranges = schemaValue.split(",");
        if (ranges.length != 2) {
            return FormEngineError.RANG_LENGTH_ERROR.getMsg();
        }
        boolean ret = isInRange(getParseValue(dataValue), getParseValue(ranges[0]), getParseValue(ranges[1]));
        if (!ret) {
            return FormEngineError.NUM_NOT_IN_RANGE.getMsg();
        }
        return null;

    }

    protected abstract boolean isInRange(T dataValue, T min, T max);

    protected abstract T getParseValue(String value);
}
