package com.xli.cache.result;



import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CacheGetResult<V> extends CacheResult {

    private volatile V value;

    private volatile CacheValueHolder<V> holder;

    public static final CacheGetResult NOT_EXISTS_WITHOUT_MSG = new CacheGetResult<>(CacheResultCode.NOT_EXISTS, null, null);

    public static final CacheGetResult EXPIRED_WITHOUT_MSG = new CacheGetResult<>(CacheResultCode.EXPIRED, null ,null);

    public CacheGetResult(CacheResultCode resultCode, String message, CacheValueHolder<V> holder) {
        super(CompletableFuture.completedFuture(new ResultData(resultCode, message, holder)));
    }

    public CacheGetResult(CompletionStage<ResultData> future) {
        super(future);
    }

    public CacheGetResult(Throwable ex) {
        super(ex);
    }

    public V getValue() {
        waitForResult();
        return value;
    }

    @Override
    protected void fetchResultSuccess(ResultData resultData) {
        holder = (CacheValueHolder<V>) resultData.getOriginData();
        value = (V) unwrapValue(holder);
        super.fetchResultSuccess(resultData);
    }

    static Object unwrapValue(Object holder) {
        Object v = holder;
        while (v instanceof CacheValueHolder) {
            v = ((CacheValueHolder<?>) v).getValue();
        }
        return v;
    }


    @Override
    protected void fetchResultFail(Throwable e) {
        value = null;
        super.fetchResultFail(e);
    }

    public CacheValueHolder<V> getHolder() {
        waitForResult();
        return holder;
    }
}
