package com.xli.cache.support;


import com.alibaba.fastjson2.JSON;

public class Fastjson2ValueEncoder extends AbstractJsonEncoder {

    public static final Fastjson2ValueEncoder INSTANCE = new Fastjson2ValueEncoder(true);

    public Fastjson2ValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected byte[] encodeSingleValue(Object value) {
        return JSON.toJSONBytes(value);
    }

}
