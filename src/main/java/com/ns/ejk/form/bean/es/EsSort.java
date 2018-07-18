package com.ns.ejk.form.bean.es;

import io.searchbox.core.search.sort.Sort;

public class EsSort extends Sort {
    public EsSort() {
        super("id", Sorting.ASC);
    }

    public EsSort(String field) {
        super(field);
    }

    public EsSort(String field, Sorting order) {
        super(field, order);
    }
}
