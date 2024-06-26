package com.xli.cache.multi;

import com.xli.cache.result.CacheGetResult;
import com.xli.cache.result.CacheResult;
import com.xli.cache.result.CacheResultCode;
import com.xli.cache.result.ResultData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MultiGetResult<K, V> extends CacheResult {

    private volatile Map<K, CacheGetResult<V>> values;

    public MultiGetResult(CompletionStage<ResultData> future) {
        super(future);
    }

    public MultiGetResult(CacheResultCode resultCode, String message, Map<K, CacheGetResult<V>> values) {
        super(CompletableFuture.completedFuture(new ResultData(resultCode, message, values)));
    }

    public MultiGetResult(Throwable e) {
        super(e);
    }

    public Map<K, CacheGetResult<V>> getValues() {
        waitForResult();
        return values;
    }

    @Override
    protected void fetchResultSuccess(ResultData resultData) {
        values = (Map<K, CacheGetResult<V>>) resultData.getOriginData();
        super.fetchResultSuccess(resultData);
    }

    @Override
    protected void fetchResultFail(Throwable e) {
        values = null;
        super.fetchResultFail(e);
    }

    public Map<K, V> unwrapValues() {
        waitForResult();
        if (values == null) {
            return null;
        }
        Map<K, V> m = new HashMap<>();
        values.forEach((key, value) -> {
            if (value.isSuccess()) {
                m.put(key, value.getValue());
            }
        });
        return m;
    }
}
