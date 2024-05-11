package com.xli.cache.support;

import com.alibaba.fastjson2.JSON;

import java.nio.charset.StandardCharsets;

public class Fastjson2ValueDecoder extends AbstractJsonDecoder {

    public static final Fastjson2ValueDecoder INSTANCE = new Fastjson2ValueDecoder(true);

    public Fastjson2ValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object parseObject(byte[] buffer, int index, int len, Class clazz) {
        String s = new String(buffer, index, len, StandardCharsets.UTF_8);
        return JSON.parseObject(s, clazz);
    }
}
