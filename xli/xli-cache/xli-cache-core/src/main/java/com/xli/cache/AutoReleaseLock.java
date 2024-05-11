package com.xli.cache;

public interface AutoReleaseLock extends AutoCloseable {

    @Override
    void close();
}
