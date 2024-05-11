package com.xli.cache.anno.support;

import com.xli.cache.anno.KeyConvertor;
import com.xli.cache.exception.CacheConfigException;
import com.xli.cache.support.Fastjson2KeyConvertor;
import com.xli.cache.support.FastjsonKeyConvertor;
import com.xli.cache.support.JacksonKeyConvertor;

import java.util.function.Function;

public class DefaultKeyConvertorParser implements KeyConvertorParser {
    @Override
    public Function<Object, Object> parseKeyConvertor(String convertor) {
        if (convertor == null) {
            return null;
        }
        if (KeyConvertor.FASTJSON.equalsIgnoreCase(convertor)) {
            return FastjsonKeyConvertor.INSTANCE;
        } else if (KeyConvertor.FASTJSON2.equalsIgnoreCase(convertor)) {
            return Fastjson2KeyConvertor.INSTANCE;
        } else if (KeyConvertor.JACKSON.equalsIgnoreCase(convertor)) {
            return JacksonKeyConvertor.INSTANCE;
        } else if (KeyConvertor.NONE.equalsIgnoreCase(convertor)) {
            return KeyConvertor.NONE_INSTANCE;
        }
        throw new CacheConfigException("not supported:" + convertor);
    }
}
