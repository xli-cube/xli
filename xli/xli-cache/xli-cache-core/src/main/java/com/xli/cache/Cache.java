package com.xli.cache;

import com.xli.cache.config.CacheConfig;
import com.xli.cache.exception.CacheInvokeException;
import com.xli.cache.multi.MultiGetResult;
import com.xli.cache.result.CacheGetResult;
import com.xli.cache.result.CacheResult;
import com.xli.cache.result.CacheResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Closeable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface Cache<K, V> extends Closeable {

    Logger logger = LoggerFactory.getLogger(Cache.class);

    @Override
    default void close() {
    }

    CacheConfig<K, V> config();

    <T> T unwrap(Class<T> clazz);

    default AutoReleaseLock tryLock(K key, long expire, TimeUnit timeUnit) {
        if (key == null) {
            return null;
        }
        final String uuid = UUID.randomUUID().toString();
        final long expireTimestamp = System.currentTimeMillis() + timeUnit.toMillis(expire);
        final CacheConfig<K, V> config = config();

        AutoReleaseLock lock = () -> {
            int unlockCount = 0;
            while (unlockCount++ < config.getTryLockUnlockCount()) {
                if(System.currentTimeMillis() < expireTimestamp) {
                    CacheResult unlockResult = REMOVE(key);
                    if (unlockResult.getResultCode() == CacheResultCode.FAIL
                            || unlockResult.getResultCode() == CacheResultCode.PART_SUCCESS) {
                        logger.info("[tryLock] [{} of {}] [{}] unlock failed. Key={}, msg = {}",
                                unlockCount, config.getTryLockUnlockCount(), uuid, key, unlockResult.getMessage());
                    } else if (unlockResult.isSuccess()) {
                        logger.debug("[tryLock] [{} of {}] [{}] successfully release the lock. Key={}",
                                unlockCount, config.getTryLockUnlockCount(), uuid, key);
                        return;
                    } else {
                        logger.warn("[tryLock] [{} of {}] [{}] unexpected unlock result: Key={}, result={}",
                                unlockCount, config.getTryLockUnlockCount(), uuid, key, unlockResult.getResultCode());
                        return;
                    }
                } else {
                    logger.info("[tryLock] [{} of {}] [{}] lock already expired: Key={}",
                            unlockCount, config.getTryLockUnlockCount(), uuid, key);
                    return;
                }
            }
        };

        int lockCount = 0;
        Cache cache = this;
        while (lockCount++ < config.getTryLockLockCount()) {
            CacheResult lockResult = cache.PUT_IF_ABSENT(key, uuid, expire, timeUnit);
            if (lockResult.isSuccess()) {
                logger.debug("[tryLock] [{} of {}] [{}] successfully get a lock. Key={}",
                        lockCount, config.getTryLockLockCount(), uuid, key);
                return lock;
            } else if (lockResult.getResultCode() == CacheResultCode.FAIL || lockResult.getResultCode() == CacheResultCode.PART_SUCCESS) {
                logger.info("[tryLock] [{} of {}] [{}] cache access failed during get lock, will inquiry {} times. Key={}, msg={}",
                        lockCount, config.getTryLockLockCount(), uuid,
                        config.getTryLockInquiryCount(), key, lockResult.getMessage());
                int inquiryCount = 0;
                while (inquiryCount++ < config.getTryLockInquiryCount()) {
                    CacheGetResult inquiryResult = cache.GET(key);
                    if (inquiryResult.isSuccess()) {
                        if (uuid.equals(inquiryResult.getValue())) {
                            logger.debug("[tryLock] [{} of {}] [{}] successfully get a lock after inquiry. Key={}",
                                    inquiryCount, config.getTryLockInquiryCount(), uuid, key);
                            return lock;
                        } else {
                            logger.debug("[tryLock] [{} of {}] [{}] not the owner of the lock, return null. Key={}",
                                    inquiryCount, config.getTryLockInquiryCount(), uuid, key);
                            return null;
                        }
                    } else {
                        logger.info("[tryLock] [{} of {}] [{}] inquiry failed. Key={}, msg={}",
                                inquiryCount, config.getTryLockInquiryCount(), uuid, key, inquiryResult.getMessage());
                    }
                }
            } else {
                logger.debug("[tryLock] [{} of {}] [{}] others holds the lock, return null. Key={}",
                        lockCount, config.getTryLockLockCount(), uuid, key);
                return null;
            }
        }

        logger.debug("[tryLock] [{}] return null after {} attempts. Key={}", uuid, config.getTryLockLockCount(), key);
        return null;
    }

    default boolean tryLockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action){
        try (AutoReleaseLock lock = tryLock(key, expire, timeUnit)) {
            if (lock != null) {
                action.run();
                return true;
            } else {
                return false;
            }
        }
    }

    default V get(K key) throws CacheInvokeException {
        CacheGetResult<V> result = GET(key);
        if (result.isSuccess()) {
            return result.getValue();
        } else {
            return null;
        }
    }

    default Map<K, V> getAll(Set<? extends K> keys) throws CacheInvokeException {
        MultiGetResult<K, V> cacheGetResults = GET_ALL(keys);
        return cacheGetResults.unwrapValues();
    }

    default void put(K key, V value) {
        PUT(key, value);
    }

    default void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        PUT(key, value, expireAfterWrite, timeUnit);
    }

    default void putAll(Map<? extends K, ? extends V> map) {
        PUT_ALL(map);
    }

    default void putAll(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        PUT_ALL(map, expireAfterWrite, timeUnit);
    }

    default boolean remove(K key) {
        return REMOVE(key).isSuccess();
    }

    default void removeAll(Set<? extends K> keys) {
        REMOVE_ALL(keys);
    }

    CacheGetResult<V> GET(K key);

    MultiGetResult<K, V> GET_ALL(Set<? extends K> keys);

    default CacheResult PUT(K key, V value) {
        if (key == null) {
            return CacheResult.FAIL_ILLEGAL_ARGUMENT;
        }
        return PUT(key, value, config().getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS);
    }

    CacheResult PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit);

    default CacheResult PUT_ALL(Map<? extends K, ? extends V> map) {
        if (map == null) {
            return CacheResult.FAIL_ILLEGAL_ARGUMENT;
        }
        return PUT_ALL(map, config().getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS);
    }

    CacheResult PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit);

    CacheResult REMOVE(K key);

    CacheResult REMOVE_ALL(Set<? extends K> keys);

    default boolean putIfAbsent(K key, V value) {
        CacheResult result = PUT_IF_ABSENT(key, value, config().getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS);
        return result.getResultCode() == CacheResultCode.SUCCESS;
    }

    CacheResult PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit);

    default V computeIfAbsent(K key, Function<K, V> loader) {
        return computeIfAbsent(key, loader, config().isCacheNullValue());
    }

    V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull);

    V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull, long expireAfterWrite, TimeUnit timeUnit);
}
