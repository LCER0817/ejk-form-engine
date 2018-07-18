package com.ns.ejk.form.util.validate;

import org.springframework.stereotype.Component;

/**
 * Created by liqiuwei on 2018/1/4.
 */
@Component
public class LengthRangeValidator extends AbsRangeValidator<String> {
    @Override
    protected boolean isInRange(String dataValue, String min, String max) {
        int length = dataValue.length();
        return length >= Integer.valueOf(min) && length <= Integer.valueOf(max);
    }

    @Override
    protected String getParseValue(String value) {
        return value;
    }

}
