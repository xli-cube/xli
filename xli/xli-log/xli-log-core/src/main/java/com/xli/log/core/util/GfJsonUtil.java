package com.xli.log.core.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public abstract class GfJsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parseObject(String jsonString, Class<T> classz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(jsonString, classz);
        } catch (JsonProcessingException e) {
        }
        return null;
    }

    public static String toJSONString(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {

        }
        return null;
    }

    public static <K, V> Map<K, V> json2Map(String jsonData, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
        try {
            return mapper.readValue(jsonData, javaType);
        } catch (Exception e) {
        }
        return null;
    }

}
