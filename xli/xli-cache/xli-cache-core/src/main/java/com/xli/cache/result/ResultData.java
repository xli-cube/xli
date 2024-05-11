package com.xli.cache.result;

import lombok.Getter;
import lombok.Setter;

@Setter
public class ResultData {

    @Getter
    private CacheResultCode resultCode;

    @Getter
    private String message;

    private Object data;

    public ResultData(Throwable e) {
        this.resultCode = CacheResultCode.FAIL;
        this.message = "Ex : " + e.getClass() + ", " + e.getMessage();
    }

    public ResultData(CacheResultCode resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return CacheGetResult.unwrapValue(data);
    }

    public Object getOriginData() {
        return data;
    }
}
