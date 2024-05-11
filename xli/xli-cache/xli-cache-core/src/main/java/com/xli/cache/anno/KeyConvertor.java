package com.xli.cache.anno;

import java.util.function.Function;

public interface KeyConvertor extends Function<Object, Object> {

    String NONE = "NONE";

    String FASTJSON = "FASTJSON";

    String JACKSON = "JACKSON";

    String FASTJSON2 = "FASTJSON2";

    Function<Object, Object> NONE_INSTANCE = k -> k;
}
