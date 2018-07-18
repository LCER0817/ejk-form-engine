package com.ns.ejk.form.util.converter;


import com.google.common.collect.Maps;

import java.util.Map;

public class ConvertorManager {

    private static Map<Long, Convertor> convertors = Maps.newHashMap();

    static {
//    convertors.put(1L,new NumberConvertor().setMapping(MappingManager.getMapping(1L))


    }

    @SuppressWarnings("rawtypes")
    public static Convertor getConvertor(long schemaId) {
        return convertors.get(schemaId);
    }
}
