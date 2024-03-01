package com.xli.cache.config;

import com.xli.cache.exception.CacheException;
import com.xli.cache.listener.service.ICacheExpiredListener;
import com.xli.cache.policy.CacheObject;
import com.xli.cache.policy.none.NullCacheProvider;
import com.xli.cache.policy.redis.RedisCacheProvider;
import com.xli.cache.service.ICacheProvider;
import com.xli.cache.service.ILevel1Cache;
import com.xli.cache.service.ILevel2Cache;
import com.xli.cache.service.impl.EhCache3Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * 两级缓存管理器
 */
@Slf4j
public class CacheProviderHolder {

    private ICacheProvider l1_provider;
    private ICacheProvider l2_provider;

    private ICacheExpiredListener listener;

    /**
     * 加载两级缓存
     *
     * @param listener
     * @return
     */
    public static CacheProviderHolder init(CacheConfig config, ICacheExpiredListener listener) {
        CacheProviderHolder holder = new CacheProviderHolder();
        holder.listener = listener;
        holder.l1_provider = loadProviderInstance("ehcache3");

        if (holder.l1_provider.isLevel(CacheObject.LEVEL_1)) {
            throw new CacheException(holder.l1_provider.getClass().getName() + " is not level_1 cache provider");
        }
        holder.l1_provider.start(config.getL1CacheProperties());
        log.info("Using L1 CacheProvider : {}", holder.l1_provider.getClass().getName());

        holder.l2_provider = loadProviderInstance("redis");
        if (holder.l2_provider.isLevel(CacheObject.LEVEL_2)) {
            throw new CacheException(holder.l2_provider.getClass().getName() + " is not level_2 cache provider");
        }
        holder.l2_provider.start(config.getL2CacheProperties());
        log.info("Using L2 CacheProvider : {}", holder.l2_provider.getClass().getName());
        return holder;
    }

    private static ICacheProvider loadProviderInstance(String cacheIdent) {
        switch (cacheIdent.toLowerCase()) {
            case "ehcache3":
                return new EhCache3Provider();
            case "redis":
                return new RedisCacheProvider();
            default:
                return new NullCacheProvider();
        }
    }

    public ILevel1Cache getLevel1Cache(String region) {
        return (ILevel1Cache) l1_provider.buildCache(region, listener);
    }

    public ILevel1Cache getLevel1Cache(String region, long timeToLiveSeconds) {
        return (ILevel1Cache) l1_provider.buildCache(region, timeToLiveSeconds, listener);
    }

    public ILevel2Cache getLevel2Cache(String region) {
        return (ILevel2Cache) l2_provider.buildCache(region, listener);
    }

    public ICacheProvider getL1Provider() {
        return l1_provider;
    }

    public ICacheProvider getL2Provider() {
        return l2_provider;
    }

    /**
     * 关闭缓存
     */
    public void shutdown() {
        l1_provider.stop();
        l2_provider.stop();
    }
}
