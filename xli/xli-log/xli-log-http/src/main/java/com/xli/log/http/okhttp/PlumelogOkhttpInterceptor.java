package com.xli.log.http.okhttp;

import com.xli.log.core.TraceId;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.xli.log.core.constant.LogMessageConstant.TRACE_ID;

public class PlumelogOkhttpInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String traceId = TraceId.getTraceId(TraceId.logTraceID.get());
        Request request = chain.request().newBuilder()
                .addHeader(TRACE_ID, traceId)
                .build();
        return chain.proceed(request);
    }
}
