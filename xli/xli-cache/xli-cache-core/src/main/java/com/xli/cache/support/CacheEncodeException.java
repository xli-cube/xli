package com.xli.cache.support;


import com.xli.cache.exception.CacheException;

import java.io.Serial;

public class CacheEncodeException extends CacheException {

    @Serial
    private static final long serialVersionUID = 6336544616682925704L;

    public CacheEncodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheEncodeException(String message) {
        super(message);
    }

}
