package com.ns.ejk.form.bean.es;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.Map;

public class QueryBuilderJsonDeserializer extends JsonDeserializer<QueryBuilder> {
    private static final String TYPE = "@Type";

    @Override
    public QueryBuilder deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, Object> jsonMap = objectMapper.readValue(p, new TypeReference<Map<String, Object>>() {
        });
        if (MapUtils.isEmpty(jsonMap)) {
            return null;
        }
        Object clazzObj = jsonMap.remove(TYPE);
        if (clazzObj == null) {
            throw new RuntimeException(String.format(" %s property Not found", TYPE));
        }
        try {
            Class clazz = Class.forName(clazzObj.toString());
            try {
                Class<? extends QueryBuilder> convClazz = clazz.asSubclass(QueryBuilder.class);
                return objectMapper.readValue(objectMapper.writeValueAsString(jsonMap), convClazz);
            } catch (ClassCastException e) {
                throw new RuntimeException(String.format("\"%s\" is not instance of \"org.apache.lucene.util.QueryBuilder\"", clazz.getName()));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("%s:%s Not Found", TYPE, clazzObj), e);
        }
    }
}
