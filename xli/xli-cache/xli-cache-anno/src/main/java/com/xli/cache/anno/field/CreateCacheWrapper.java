package com.xli.cache.anno.field;


import com.xli.cache.Cache;
import com.xli.cache.anno.CacheConstants;
import com.xli.cache.anno.CachePenetrationProtect;
import com.xli.cache.anno.CacheRefresh;
import com.xli.cache.anno.CreateCache;
import com.xli.cache.anno.method.CacheConfigUtil;
import com.xli.cache.anno.support.CacheNameGenerator;
import com.xli.cache.anno.support.ConfigProvider;
import com.xli.cache.anno.support.GlobalCacheConfig;
import com.xli.cache.anno.support.PenetrationProtectConfig;
import com.xli.cache.manager.CacheManager;
import com.xli.cache.anno.support.CachedAnnoConfig;
import com.xli.cache.refresh.RefreshPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Field;

class CreateCacheWrapper {

    private static final Logger logger = LoggerFactory.getLogger(CreateCacheWrapper.class);

    private Cache cache;

    private ConfigurableListableBeanFactory beanFactory;
    private CreateCache ann;
    private Field field;
    private RefreshPolicy refreshPolicy;
    private PenetrationProtectConfig protectConfig;

    public CreateCacheWrapper(ConfigurableListableBeanFactory beanFactory, CreateCache ann, Field field) {
        this.beanFactory = beanFactory;
        this.ann = ann;
        this.field = field;
        CacheRefresh cr = field.getAnnotation(CacheRefresh.class);
        if (cr != null) {
            refreshPolicy = CacheConfigUtil.parseRefreshPolicy(cr);
        }
        CachePenetrationProtect penetrateProtect = field.getAnnotation(CachePenetrationProtect.class);
        if (penetrateProtect != null) {
            protectConfig = CacheConfigUtil.parsePenetrationProtectConfig(penetrateProtect);
        }
        init();
    }

    private void init() {
        GlobalCacheConfig globalCacheConfig = beanFactory.getBean(GlobalCacheConfig.class);
        ConfigProvider configProvider = beanFactory.getBean(ConfigProvider.class);
        CacheManager cacheManager = beanFactory.getBean(CacheManager.class);
        if (cacheManager == null) {
            logger.error("There is no cache manager instance in spring context");
        }

        CachedAnnoConfig cac = new CachedAnnoConfig();
        cac.setArea(ann.area());
        cac.setName(ann.name());
        cac.setTimeUnit(ann.timeUnit());
        cac.setExpire(ann.expire());
        cac.setLocalExpire(ann.localExpire());
        cac.setCacheType(ann.cacheType());
        cac.setSyncLocal(ann.syncLocal());
        cac.setLocalLimit(ann.localLimit());
        cac.setSerialPolicy(ann.serialPolicy());
        cac.setKeyConvertor(ann.keyConvertor());

        cac.setRefreshPolicy(refreshPolicy);
        cac.setPenetrationProtectConfig(protectConfig);

        String cacheName = cac.getName();
        if (CacheConstants.isUndefined(cacheName)) {
            String[] hiddenPackages = globalCacheConfig.getHiddenPackages();
            CacheNameGenerator g = configProvider.createCacheNameGenerator(hiddenPackages);
            cacheName = g.generateCacheName(field);
        }
        cache = configProvider.newContext(cacheManager).__createOrGetCache(cac, ann.area(), cacheName);
    }

    public Cache getCache() {
        return cache;
    }
}
