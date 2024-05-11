package com.xli.cache.config;

import com.xli.cache.anno.CacheConstants;
import com.xli.cache.exception.CacheException;
import com.xli.cache.loader.CacheLoader;
import com.xli.cache.monitor.CacheMonitor;
import com.xli.cache.refresh.RefreshPolicy;
import lombok.Data;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
public class CacheConfig<K, V> implements Cloneable {

    private long expireAfterWriteInMillis = CacheConstants.DEFAULT_EXPIRE * 1000L;

    private long expireAfterAccessInMillis = 0;

    private Function<K, Object> keyConvertor;

    private CacheLoader<K, V> loader;

    private List<CacheMonitor> monitors = new ArrayList<>();

    private boolean cacheNullValue = false;

    private RefreshPolicy refreshPolicy;

    private int tryLockUnlockCount = 2;

    private int tryLockInquiryCount = 1;

    private int tryLockLockCount = 2;

    private boolean cachePenetrationProtect = false;
    private Duration penetrationProtectTimeout = null;

    @Override
    public CacheConfig clone() {
        try {
            CacheConfig copy = (CacheConfig) super.clone();
            if (monitors != null) {
                copy.monitors = new ArrayList(this.monitors);
            }
            if (refreshPolicy != null) {
                copy.refreshPolicy = this.refreshPolicy.clone();
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new CacheException(e);
        }
    }

    public boolean isExpireAfterAccess() {
        return expireAfterAccessInMillis > 0;
    }

    public boolean isExpireAfterWrite() {
        return expireAfterWriteInMillis > 0;
    }
}
