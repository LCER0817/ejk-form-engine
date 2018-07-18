package com.ns.ejk.form.bean.act;

import com.ns.common.bean.EjkPage;
import com.ns.ejk.form.bean.es.EsSearch;

public class FormDataReq {
    private String schemaName;
    private EsSearch esSearch;
    private EjkPage ejkPage;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public EsSearch getEsSearch() {
        return esSearch;
    }

    public void setEsSearch(EsSearch esSearch) {
        this.esSearch = esSearch;
    }

    public EjkPage getEjkPage() {
        return ejkPage;
    }

    public void setEjkPage(EjkPage ejkPage) {
        this.ejkPage = ejkPage;
    }
}
