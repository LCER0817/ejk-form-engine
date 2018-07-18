package com.ns.ejk.form.util.converter;

import com.ns.ejk.form.bean.DataItem;
import com.ns.ejk.form.bean.SchemaItem;
import com.ns.ejk.form.bean.SchemaItemValidate;
import com.ns.ejk.form.util.mapping.Mapping;

import java.util.List;
import java.util.Map;

public abstract class Convertor<S, D> {

    private Mapping mapping;

    public abstract Map<SchemaItem, List<SchemaItemValidate>> convertSchema(S schema);

    public abstract List<DataItem> convertData(D data);

    protected Convertor<S, D> setMapping(Mapping mapping) {
        this.mapping = mapping;
        return this;
    }
}
