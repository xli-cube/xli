package com.xli.log.core;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.xli.log.core.util.IdWorker;

import java.util.UUID;

/**
 * className：TraceId
 * description：TraceId 用来存储traceID相关信息
 */
public class TraceId {
    public static TransmittableThreadLocal<String> logTraceID = new TransmittableThreadLocal<>();
    public static IdWorker worker = new IdWorker(1, 1, 1);

    public static void set() {
        logTraceID.set(String.valueOf(worker.nextId()));
    }

    public static void setSofa() {
        String traceId = TraceIdGenerator.generate();
        logTraceID.set(traceId);
    }

    public static String getTraceId(String traceId) {
        if (traceId == null || traceId.isEmpty()) {
            traceId = String.valueOf(worker.nextId());
            TraceId.logTraceID.set(traceId);
        }
        return traceId;
    }
}
