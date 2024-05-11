package com.xli.cache.anno.support;

import com.xli.cache.Cache;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.function.Function;

@Setter
@Getter
public class CacheAnnoConfig {
    private String area;
    private String name;
    private String key;
    private String condition;

    private Function<Object, Boolean> conditionEvaluator;
    private Function<Object, Object> keyEvaluator;
    private Cache<?, ?> cache;
    private Method defineMethod;

}
