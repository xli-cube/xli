package com.xli.cache.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface CacheRefresh {

    int refresh();

    int stopRefreshAfterLastAccess() default CacheConstants.UNDEFINED_INT;

    int refreshLockTimeout() default CacheConstants.UNDEFINED_INT;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
