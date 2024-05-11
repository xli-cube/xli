package com.xli.cache.anno.method;

import com.xli.cache.Cache;
import com.xli.cache.anno.support.CacheAnnoConfig;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

@Setter
@Getter
public class CacheInvokeContext {
    private Invoker invoker;
    private Method method;
    private Object[] args;
    private CacheInvokeConfig cacheInvokeConfig;
    private Object targetObject;
    private Object result;

    private BiFunction<CacheInvokeContext, CacheAnnoConfig, Cache> cacheFunction;
    private String[] hiddenPackages;

    public CacheInvokeContext(){
    }


}
