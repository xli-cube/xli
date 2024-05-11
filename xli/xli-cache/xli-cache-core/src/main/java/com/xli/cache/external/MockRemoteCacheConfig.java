package com.xli.cache.external;


import com.xli.cache.anno.CacheConstants;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MockRemoteCacheConfig<K, V> extends ExternalCacheConfig<K, V> {

    private int limit = CacheConstants.DEFAULT_LOCAL_LIMIT;

}
