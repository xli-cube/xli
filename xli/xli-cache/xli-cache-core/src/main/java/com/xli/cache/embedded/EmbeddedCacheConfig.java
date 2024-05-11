package com.xli.cache.embedded;

import com.xli.cache.anno.CacheConstants;
import com.xli.cache.config.CacheConfig;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmbeddedCacheConfig<K, V> extends CacheConfig<K, V> {

    private int limit = CacheConstants.DEFAULT_LOCAL_LIMIT;

}
