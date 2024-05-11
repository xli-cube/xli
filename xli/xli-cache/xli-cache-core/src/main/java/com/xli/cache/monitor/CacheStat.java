package com.xli.cache.monitor;


import com.xli.cache.exception.CacheException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class CacheStat implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 4593984293179646403L;

    protected String cacheName;
    protected long statStartTime;
    protected long statEndTime;

    protected long getCount;
    protected long getHitCount;
    protected long getMissCount;
    protected long getFailCount;
    protected long getExpireCount;
    protected long getTimeSum;
    protected long minGetTime = Long.MAX_VALUE;
    protected long maxGetTime = 0;

    protected long putCount;
    protected long putSuccessCount;
    protected long putFailCount;
    protected long putTimeSum;
    protected long minPutTime = Long.MAX_VALUE;
    protected long maxPutTime = 0;

    protected long removeCount;
    protected long removeSuccessCount;
    protected long removeFailCount;
    protected long removeTimeSum;
    protected long minRemoveTime = Long.MAX_VALUE;
    protected long maxRemoveTime = 0;

    protected long loadCount;
    protected long loadSuccessCount;
    protected long loadFailCount;
    protected long loadTimeSum;
    protected long minLoadTime = Long.MAX_VALUE;
    protected long maxLoadTime = 0;

    @Override
    public CacheStat clone() {
        try {
            return (CacheStat) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CacheException(e);
        }
    }

    private double tps(long count){
        long t = statEndTime;
        if (t == 0) {
            t = System.currentTimeMillis();
        }
        t = t - statStartTime;
        if (t == 0) {
            return 0;
        } else {
            return 1000.0 * count / t;
        }
    }

    public double qps() {
        return tps(getCount);
    }

    public double putTps() {
        return tps(putCount);
    }

    public double removeTps() {
        return tps(removeCount);
    }

    public double loadQps() {
        return tps(loadCount);
    }

    public double hitRate() {
        if (getCount == 0) {
            return 0;
        }
        return 1.0 * getHitCount / getCount;
    }

    public double avgGetTime() {
        if (getCount == 0) {
            return 0;
        }
        return 1.0 * getTimeSum / getCount;
    }

    public double avgPutTime() {
        if (putCount == 0) {
            return 0;
        }
        return 1.0 * putTimeSum / putCount;
    }

    public double avgRemoveTime() {
        if (removeCount == 0) {
            return 0;
        }
        return 1.0 * removeTimeSum / removeCount;
    }

    public double avgLoadTime() {
        if (loadCount == 0) {
            return 0;
        }
        return 1.0 * loadTimeSum / loadCount;
    }
}
