package com.xli.log.core.jakarta;

import com.xli.log.core.TraceId;
import com.xli.log.core.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class PlumeLogJakartaTraceIdInterceptor  implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader("traceId");
        if (StringUtils.isEmpty(traceId)) {
            TraceId.set();
        } else {
            TraceId.logTraceID.set(traceId);
        }
        return true;
    }
}
