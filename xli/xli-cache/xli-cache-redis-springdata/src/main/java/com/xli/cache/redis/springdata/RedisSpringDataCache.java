package com.xli.cache.redis.springdata;

import com.xli.cache.exception.CacheConfigException;
import com.xli.cache.external.AbstractExternalCache;
import com.xli.cache.multi.MultiGetResult;
import com.xli.cache.result.CacheGetResult;
import com.xli.cache.result.CacheResult;
import com.xli.cache.result.CacheResultCode;
import com.xli.cache.result.CacheValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class RedisSpringDataCache<K, V> extends AbstractExternalCache<K, V> {

    private final Logger logger = LoggerFactory.getLogger(RedisSpringDataCache.class);

    private final RedisConnectionFactory connectionFactory;
    private final RedisSpringDataCacheConfig<K, V> config;

    private final Function<Object, byte[]> valueEncoder;
    private final Function<byte[], Object> valueDecoder;

    public RedisSpringDataCache(RedisSpringDataCacheConfig<K, V> config) {
        super(config);
        this.connectionFactory = config.getConnectionFactory();
        if (connectionFactory == null) {
            throw new CacheConfigException("connectionFactory is required");
        }
        this.config = config;
        this.valueEncoder = config.getValueEncoder();
        this.valueDecoder = config.getValueDecoder();
    }

    private void closeConnection(RedisConnection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
            logger.error("RedisConnection close fail: {}, {}", ex.getMessage(), ex.getClass().getName());
        }
    }

    @Override
    protected CacheGetResult<V> do_GET(K key) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            byte[] newKey = buildKey(key);
            byte[] resultBytes = con.get(newKey);
            if (resultBytes != null) {
                CacheValueHolder<V> holder = (CacheValueHolder<V>) valueDecoder.apply(resultBytes);
                if (System.currentTimeMillis() >= holder.getExpireTime()) {
                    return CacheGetResult.EXPIRED_WITHOUT_MSG;
                }
                return new CacheGetResult(CacheResultCode.SUCCESS, null, holder);
            } else {
                return CacheGetResult.NOT_EXISTS_WITHOUT_MSG;
            }
        } catch (Exception ex) {
            logError("GET", key, ex);
            return new CacheGetResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected MultiGetResult<K, V> do_GET_ALL(Set<? extends K> keys) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            ArrayList<K> keyList = new ArrayList<>(keys);
            byte[][] newKeys = keyList.stream().map(this::buildKey).toArray(byte[][]::new);

            Map<K, CacheGetResult<V>> resultMap = new HashMap<>();
            if (newKeys.length > 0) {
                List<byte[]> mgetResults = con.mGet(newKeys);
                if (mgetResults != null) {
                    for (int i = 0; i < mgetResults.size(); i++) {
                        Object value = mgetResults.get(i);
                        K key = keyList.get(i);
                        if (value != null) {
                            CacheValueHolder<V> holder = (CacheValueHolder<V>) valueDecoder.apply((byte[]) value);
                            if (System.currentTimeMillis() >= holder.getExpireTime()) {
                                resultMap.put(key, CacheGetResult.EXPIRED_WITHOUT_MSG);
                            } else {
                                CacheGetResult<V> r = new CacheGetResult<>(CacheResultCode.SUCCESS, null, holder);
                                resultMap.put(key, r);
                            }
                        } else {
                            resultMap.put(key, CacheGetResult.NOT_EXISTS_WITHOUT_MSG);
                        }
                    }
                }
            }
            return new MultiGetResult<>(CacheResultCode.SUCCESS, null, resultMap);
        } catch (Exception ex) {
            logError("GET_ALL", "keys(" + keys.size() + ")", ex);
            return new MultiGetResult<>(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected CacheResult do_PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            CacheValueHolder<V> holder = new CacheValueHolder<>(value, timeUnit.toMillis(expireAfterWrite));
            byte[] keyBytes = buildKey(key);
            byte[] valueBytes = valueEncoder.apply(holder);
            Boolean result = con.pSetEx(keyBytes, timeUnit.toMillis(expireAfterWrite), valueBytes);
            if (Boolean.TRUE.equals(result)) {
                return CacheResult.SUCCESS_WITHOUT_MSG;
            } else {
                return new CacheResult(CacheResultCode.FAIL, "result:" + result);
            }
        } catch (Exception ex) {
            logError("PUT", key, ex);
            return new CacheResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected CacheResult do_PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            int failCount = 0;
            for (Map.Entry<? extends K, ? extends V> en : map.entrySet()) {
                CacheValueHolder<V> holder = new CacheValueHolder<>(en.getValue(), timeUnit.toMillis(expireAfterWrite));
                Boolean result = con.pSetEx(buildKey(en.getKey()),
                        timeUnit.toMillis(expireAfterWrite), valueEncoder.apply(holder));
                if (!Boolean.TRUE.equals(result)) {
                    failCount++;
                }
            }
            return failCount == 0 ? CacheResult.SUCCESS_WITHOUT_MSG :
                    failCount == map.size() ? CacheResult.FAIL_WITHOUT_MSG : CacheResult.PART_SUCCESS_WITHOUT_MSG;
        } catch (Exception ex) {
            logError("PUT_ALL", "map(" + map.size() + ")", ex);
            return new CacheResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected CacheResult do_REMOVE(K key) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            byte[] keyBytes = buildKey(key);
            Long result = con.del(keyBytes);
            if (result == null) {
                return new CacheResult(CacheResultCode.FAIL, "result:" + result);
            } else if (result == 1) {
                return CacheResult.SUCCESS_WITHOUT_MSG;
            } else if (result == 0) {
                return new CacheResult(CacheResultCode.NOT_EXISTS, null);
            } else {
                return CacheResult.FAIL_WITHOUT_MSG;
            }
        } catch (Exception ex) {
            logError("REMOVE", key, ex);
            return new CacheResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected CacheResult do_REMOVE_ALL(Set<? extends K> keys) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            byte[][] newKeys = keys.stream().map(this::buildKey).toArray((len) -> new byte[keys.size()][]);
            Long result = con.del(newKeys);
            if (result != null) {
                return CacheResult.SUCCESS_WITHOUT_MSG;
            } else {
                return new CacheResult(CacheResultCode.FAIL, "result:" + result);
            }
        } catch (Exception ex) {
            logError("REMOVE_ALL", "keys(" + keys.size() + ")", ex);
            return new CacheResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    protected CacheResult do_PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        RedisConnection con = null;
        try {
            con = connectionFactory.getConnection();
            CacheValueHolder<V> holder = new CacheValueHolder<>(value, timeUnit.toMillis(expireAfterWrite));
            byte[] newKey = buildKey(key);
            Boolean result = con.set(newKey, valueEncoder.apply(holder),
                    Expiration.from(expireAfterWrite, timeUnit), RedisStringCommands.SetOption.ifAbsent());
            if (Boolean.TRUE.equals(result)) {
                return CacheResult.SUCCESS_WITHOUT_MSG;
            } else {
                return CacheResult.EXISTS_WITHOUT_MSG;
            }
        } catch (Exception ex) {
            logError("PUT_IF_ABSENT", key, ex);
            return new CacheResult(ex);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        throw new UnsupportedOperationException("RedisSpringDataCache does not support unwrap");
    }

    @Override
    public RedisSpringDataCacheConfig<K, V> config() {
        return config;
    }

}
