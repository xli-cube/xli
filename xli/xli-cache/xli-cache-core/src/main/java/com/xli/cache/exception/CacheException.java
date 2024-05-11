package com.xli.cache.exception;

import java.io.Serial;

public class CacheException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5256071780816436778L;

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
