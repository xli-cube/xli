package com.xli.cache.policy.redis;

import com.xli.cache.service.ILevel2Cache;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Redis缓存操作封装，基于region+_key实现多个Region的缓存
 */
@Slf4j
public class RedisGenericCache implements ILevel2Cache {

    private final String namespace;
    private final String region;
    private final RedisClient client;
    private final int scanCount;

    /**
     * 缓存构造
     *
     * @param namespace 命名空间，用于在多个实例中避免 _key 的重叠
     * @param region    缓存区域的名称
     * @param client    缓存客户端接口
     * @param scanCount
     */
    public RedisGenericCache(String namespace, String region, RedisClient client, int scanCount) {
        if (region == null || region.isEmpty()) {
            region = "_"; // 缺省region
        }
        this.client = client;
        this.namespace = namespace;
        this.region = _regionName(region);
        this.scanCount = scanCount;
    }

    @Override
    public boolean supportTTL() {
        return true;
    }

    @Override
    public byte[] getBytes(String key) {
        try {
            return client.get().get(_key(key));
        } finally {
            client.release();
        }
    }

    @Override
    public List<byte[]> getBytes(Collection<String> keys) {
        try (Jedis jedis = client.get()) {
            List<byte[]> result = new ArrayList<>();
            for (String key : keys) {
                byte[] value = jedis.get(_key(key));
                result.add(value);
            }
            return result;
        } finally {
            client.release();
        }
    }

    @Override
    public void setBytes(String key, byte[] bytes) {
        try {
            client.get().set(_key(key), bytes);
        } finally {
            client.release();
        }
    }

    @Override
    public void setBytes(Map<String, byte[]> bytes) {
        try (Jedis jedis = client.get()) {
            for (Map.Entry<String, byte[]> entry : bytes.entrySet()) {
                jedis.set(_key(entry.getKey()), entry.getValue());
            }
        } finally {
            client.release();
        }
    }

    @Override
    public void setBytes(String key, byte[] bytes, long timeToLiveInSeconds) {
        if (timeToLiveInSeconds <= 0) {
            log.debug(String.format("Invalid timeToLiveInSeconds value : %d , skipped it.", timeToLiveInSeconds));
            setBytes(key, bytes);
        } else {
            try {
                client.get().setex(_key(key), (int) timeToLiveInSeconds, bytes);
            } finally {
                client.release();
            }
        }
    }

    @Override
    public void setBytes(Map<String, byte[]> bytes, long timeToLiveInSeconds) {
        try {
            /* 为了支持 TTL ，没法使用批量写入方法 */
            if (timeToLiveInSeconds <= 0) {
                log.debug(String.format("Invalid timeToLiveInSeconds value : %d , skipped it.", timeToLiveInSeconds));
                setBytes(bytes);
            } else {
                bytes.forEach((k, v) -> setBytes(k, v, timeToLiveInSeconds));
            }
        } finally {
            client.release();
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return client.get().exists(_key(key));
        } finally {
            client.release();
        }
    }

    /**
     * 1、线上redis服务大概率会禁用或重命名keys命令；
     * 2、keys命令效率太低容易致使redis宕机；
     * 所以使用scan命令替换keys命令操作，增加可用性及提升执行性能
     */
    @Override
    public Collection<String> keys() {
        try (Jedis jedis = client.get()) {
            List<String> keys = new ArrayList<>();
            String cursor = "0";
            ScanParams scanParams = new ScanParams();
            scanParams.match(this.region + ":*");
            scanParams.count(scanCount);
            ScanResult<String> scan = jedis.scan(cursor, scanParams);
            while (!scan.isCompleteIteration()) {
                keys.addAll(scan.getResult());
                scan = jedis.scan(scan.getCursor(), scanParams);
            }
            return keys.stream().map(k -> k.substring(this.region.length() + 1)).collect(Collectors.toList());
        } finally {
            client.release();
        }
    }

    @Override
    public void evict(String... keys) {
        try (Jedis jedis = client.get()) {
            jedis.del(Arrays.stream(keys).map(this::_key).toArray(byte[][]::new));
        } finally {
            client.release();
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = client.get()) {
            Collection<String> keys = keys();
            if (!keys.isEmpty()) {
                jedis.del(keys.toArray(new String[0]));
            }
        } finally {
            client.release();
        }
    }


    private String _regionName(String region) {
        if (namespace != null && !namespace.trim().isEmpty()) {
            region = namespace + ":" + region;
        }
        return region;
    }

    private byte[] _key(String key) {
        return (this.region + ":" + key).getBytes(StandardCharsets.UTF_8);
    }
}
