package com.xli.log.http.restTemplate;

import com.xli.log.core.TraceId;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.xli.log.core.constant.LogMessageConstant.TRACE_ID;

public class PlumelogRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        String traceId = TraceId.getTraceId(TraceId.logTraceID.get());
        httpRequest.getHeaders().set(TRACE_ID, traceId);
        return execution.execute(httpRequest, bytes);
    }
}
