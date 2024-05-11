package com.xli.cache.exception;

import java.io.Serial;

public class CacheInvokeException extends CacheException {

    @Serial
    private static final long serialVersionUID = 172355743604204886L;

    public CacheInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheInvokeException(Throwable cause) {
        super(cause);
    }

}
