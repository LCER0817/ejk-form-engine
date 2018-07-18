package com.ns.ejk.form.util.validate;

import org.apache.commons.lang.math.NumberRange;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

/**
 * Created by liqiuwei on 2018/1/4.
 */
@Component
public class NumberRangeValidator extends AbsRangeValidator<Number> {
    @Override
    protected boolean isInRange(Number dataValue, Number min, Number max) {
        NumberRange numberRange = new NumberRange(min, max);
        return numberRange.containsNumber(dataValue);
    }

    @Override
    protected Number getParseValue(String value) {
        return NumberUtils.createNumber(value);
    }

}
