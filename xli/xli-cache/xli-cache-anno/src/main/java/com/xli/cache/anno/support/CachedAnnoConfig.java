package com.xli.cache.anno.support;

import com.xli.cache.anno.CacheType;
import com.xli.cache.refresh.RefreshPolicy;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Setter
@Getter
public class CachedAnnoConfig extends CacheAnnoConfig {

    private boolean enabled;
    private TimeUnit timeUnit;
    private long expire;
    private long localExpire;
    private CacheType cacheType;
    private boolean syncLocal;
    private int localLimit;
    private boolean cacheNullValue;
    private String serialPolicy;
    private String keyConvertor;
    private String postCondition;

    private Function<Object, Boolean> postConditionEvaluator;
    private RefreshPolicy refreshPolicy;
    private PenetrationProtectConfig penetrationProtectConfig;


}
