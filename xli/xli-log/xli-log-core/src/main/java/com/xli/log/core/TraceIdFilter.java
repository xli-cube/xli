package com.xli.log.core;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class TraceIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String traceId = request.getParameter("traceId");
            if (traceId == null || "".equals(traceId)) {
                TraceId.set();
            } else {
                TraceId.logTraceID.set(traceId);
            }
        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
