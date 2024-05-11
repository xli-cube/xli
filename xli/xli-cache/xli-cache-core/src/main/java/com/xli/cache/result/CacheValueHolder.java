package com.xli.cache.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public final class CacheValueHolder<V> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2672796913147982508L;

    private V value;

    private long expireTime;

    private long accessTime;

    public CacheValueHolder() {
    }

    public CacheValueHolder(V value, long expireAfterWrite) {
        this.value = value;
        this.accessTime = System.currentTimeMillis();
        this.expireTime = accessTime + expireAfterWrite;
    }
}
