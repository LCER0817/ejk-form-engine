package com.ns.ejk.form.bean.es;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/2/9.
 */
public class EsSearch {

    @JsonDeserialize(using = QueryBuilderJsonDeserializer.class)
    private QueryBuilder queryBuilder;

    @JsonDeserialize(contentAs = EsSort.class)
    private List<EsSort> sorts = new ArrayList<>();

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public List<EsSort> getSorts() {
        return sorts;
    }

    public void setSorts(List<EsSort> sorts) {
        this.sorts = sorts;
    }
}
