package com.xli.cache.exception;

/**
 * 反序列化的对象兼容异常
 */
public class DeserializeException extends RuntimeException {

    public DeserializeException(String message) {
        super(message);
    }
}
