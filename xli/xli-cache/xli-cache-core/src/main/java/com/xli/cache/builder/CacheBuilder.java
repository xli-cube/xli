package com.xli.cache.builder;

import com.xli.cache.Cache;

public interface CacheBuilder {
    <K, V> Cache<K, V> buildCache();
}
