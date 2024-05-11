package com.xli.cache.anno.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface CacheNameGenerator {

    String generateCacheName(Method method, Object targetObject);

    String generateCacheName(Field field);
}
