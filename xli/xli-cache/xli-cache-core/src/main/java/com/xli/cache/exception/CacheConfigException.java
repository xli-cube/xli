package com.xli.cache.exception;

import java.io.Serial;

public class CacheConfigException extends CacheException {

    @Serial
    private static final long serialVersionUID = 2145996462343503472L;

    public CacheConfigException(Throwable cause) {
        super(cause);
    }

    public CacheConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheConfigException(String message) {
        super(message);
    }
}
