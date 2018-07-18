package com.ns.ejk.form.util.bean.es;

import com.ns.ejk.form.bean.es.EsSearch;

/**
 * Created by lenovo on 2018/2/9.
 */
public class EsSearchUtil {

    public static String getQueryString(EsSearch esSearch) {
        return "{\"query\":" + esSearch.getQueryBuilder() + "}";
    }


}