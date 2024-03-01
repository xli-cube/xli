package com.xli.cache.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
public class Command {

    public final static byte OPT_JOIN 	   = 0x01;	//加入集群
    public final static byte OPT_EVICT_KEY = 0x02; 	//删除缓存
    public final static byte OPT_CLEAR_KEY = 0x03; 	//清除缓存
    public final static byte OPT_QUIT 	   = 0x04;	//退出集群

    private int src;
    private int operator;
    private String region;
    private String[] keys;

    public Command(byte o, String r, String...keys){
        this.operator = o;
        this.region = r;
        this.keys = keys;
    }

    public static int genRandomSrc() {
        long ct = System.currentTimeMillis();
        SecureRandom rndSeed = new SecureRandom();
        return (int) (rndSeed.nextInt(10000) * 1000 + ct % 1000);
    }
    public static Command join() {
        return new Command(OPT_JOIN, null);
    }

    public static Command quit() {
        return new Command(OPT_QUIT, null);
    }

    public String json() {
        return JSON.toJSONString(this);
    }

    public static Command parse(String json) {
        try {
            return JSON.parseObject(json, Command.class);
        } catch (JSONException e) {
            log.warn("Failed to parse j2cache command: {}", json, e);
        }
        return null;
    }

    public int getOperator() {
        return operator;
    }

    public String getRegion() {
        return region;
    }

    public String[] getKeys() {
        return keys;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    @Override
    public String toString(){
        return json();
    }
}
