package com.xli.cache.anno.support;

import java.util.function.Function;

public interface KeyConvertorParser {
    Function<Object, Object> parseKeyConvertor(String convertor);
}
