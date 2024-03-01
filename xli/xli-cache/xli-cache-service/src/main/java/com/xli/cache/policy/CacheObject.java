package com.xli.cache.policy;

import com.xli.cache.service.NullObject;

public class CacheObject {

    public final static byte LEVEL_1 = 1;    //一级缓存数据
    public final static byte LEVEL_2 = 2;    //二级缓存数据

    private String region;
    private String key;
    private Object value;
    private byte level;

    public CacheObject(String region, String key, byte level) {
        this(region, key, level, null);
    }

    public CacheObject(String region, String key, byte level, Object value) {
        this.region = region;
        this.key = key;
        this.level = level;
        this.value = value;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        if (value == null || value.getClass().equals(NullObject.class) || value.getClass().equals(Object.class))
            return null;
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object rawValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s,L%d]=>%s", region, key, level, getValue());
    }
}
