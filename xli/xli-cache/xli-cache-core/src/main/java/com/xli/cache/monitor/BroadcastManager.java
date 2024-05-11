package com.xli.cache.monitor;

import com.xli.cache.Cache;
import com.xli.cache.embedded.AbstractEmbeddedCache;
import com.xli.cache.exception.CacheConfigException;
import com.xli.cache.external.ExternalCacheConfig;
import com.xli.cache.manager.CacheManager;
import com.xli.cache.multi.MultiLevelCache;
import com.xli.cache.result.CacheResult;
import com.xli.cache.support.CacheUtil;
import com.xli.cache.support.SquashedLogger;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public abstract class BroadcastManager implements AutoCloseable {

    private static Logger logger = LoggerFactory.getLogger(BroadcastManager.class);

    private final String sourceId = UUID.randomUUID().toString();

    private final CacheManager cacheManager;

    public BroadcastManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    protected void checkConfig(ExternalCacheConfig config) {
        if (config.getBroadcastChannel() == null) {
            throw new CacheConfigException("BroadcastChannel not set");
        }
        if (config.getValueEncoder() == null) {
            throw new CacheConfigException("no value encoder");
        }
        if (config.getValueDecoder() == null) {
            throw new CacheConfigException("no value decoder");
        }
    }

    public abstract CacheResult publish(CacheMessage cacheMessage);

    public abstract void startSubscribe();

    @Override
    public void close() throws Exception {
    }

    protected void processNotification(byte[] message, Function<byte[], Object> decoder) {
        try {
            if (message == null) {
                logger.error("notify message is null");
                return;
            }
            Object value = decoder.apply(message);
            if (value == null) {
                logger.error("notify message is null");
                return;
            }
            if (value instanceof CacheMessage) {
                processCacheMessage((CacheMessage) value);
            } else {
                logger.error("the message is not instance of CacheMessage, class={}", value.getClass());
            }
        } catch (Throwable e) {
            SquashedLogger.getLogger(logger).error("receive cache notify error", e);
        }
    }

    private void processCacheMessage(CacheMessage cacheMessage) {
        if (sourceId.equals(cacheMessage.getSourceId())) {
            return;
        }
        Cache cache = cacheManager.getCache(cacheMessage.getArea(), cacheMessage.getCacheName());
        if (cache == null) {
            logger.warn("Cache instance not exists: {},{}", cacheMessage.getArea(), cacheMessage.getCacheName());
            return;
        }
        Cache absCache = CacheUtil.getAbstractCache(cache);
        if (!(absCache instanceof MultiLevelCache)) {
            logger.warn("Cache instance is not MultiLevelCache: {},{}", cacheMessage.getArea(), cacheMessage.getCacheName());
            return;
        }
        Cache[] caches = ((MultiLevelCache) absCache).caches();
        Set<Object> keys = Stream.of(cacheMessage.getKeys()).collect(Collectors.toSet());
        for (Cache c : caches) {
            Cache localCache = CacheUtil.getAbstractCache(c);
            if (localCache instanceof AbstractEmbeddedCache) {
                ((AbstractEmbeddedCache) localCache).__removeAll(keys);
            } else {
                break;
            }
        }
    }

}
