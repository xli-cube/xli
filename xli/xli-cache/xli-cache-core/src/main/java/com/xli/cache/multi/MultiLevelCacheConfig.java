package com.xli.cache.multi;

import com.xli.cache.Cache;
import com.xli.cache.config.CacheConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MultiLevelCacheConfig<K, V> extends CacheConfig<K, V> {

    private List<Cache<K, V>> caches = new ArrayList<>();

    private boolean useExpireOfSubCache;

    @Override
    public MultiLevelCacheConfig clone() {
        MultiLevelCacheConfig copy = (MultiLevelCacheConfig) super.clone();
        if (caches != null) {
            copy.caches = new ArrayList(this.caches);
        }
        return copy;
    }
}
