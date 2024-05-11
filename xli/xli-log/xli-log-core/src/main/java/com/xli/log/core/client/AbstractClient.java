package com.xli.log.core.client;

import com.xli.log.core.exception.LogQueueConnectException;
import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.JedisPubSub;

import java.util.*;

public abstract class AbstractClient {

    @Setter
    @Getter
    private static AbstractClient client;

    public void pushMessage(String key, String strings) throws LogQueueConnectException {

    }

    public void putMessageList(String key, List<String> list) throws LogQueueConnectException {

    }

    public List<String> getMessage(String key, int size) throws LogQueueConnectException {
        return new ArrayList<>();
    }

    public boolean setNx(String key, Integer expire) {
        return false;
    }

    public boolean existsKey(String key) {
        return true;

    }

    public String getMessage(String key) {
        return null;
    }

    public void set(String key, String value) {
    }

    public void set(String key, String value, int seconds) {
    }

    public void expireAt(String key, Long time) {
    }

    public void expire(String key, int seconds) {
    }

    public Long incr(String key) {
        return 0L;
    }

    public Long incrBy(String key, int value) {
        return 0L;
    }

    public void hset(String key, Map<String, String> value) {
    }

    public void sadd(String key, String value) {
    }

    public Set<String> smembers(String key) {
        return new HashSet<>();
    }

    public void del(String key) {
    }

    public void hset(String key, String field, String value) {
    }

    public void hdel(String key, String... field) {
    }

    public String hget(String key, String field) {
        return "";
    }

    public Long llen(String key) {
        return 0L;
    }

    public Map<String, String> hgetAll(String key) {
        return new HashMap<>();
    }

    public List<String> hmget(String key, String... field) {
        return new ArrayList<>();
    }

    public Long hincrby(String key, String field, int num) {
        return null;
    }

    public void publish(String channel, String message) {
    }

    public Long hlen(String key) {
        return null;
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channel) {
    }

}
