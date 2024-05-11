package com.xli.log.core.util;

import com.xli.log.core.TraceId;
import com.xli.log.core.TraceMessage;
import com.xli.log.core.dto.RunLogMessage;
import com.xli.log.core.dto.TraceLogMessage;

public class TraceLogMessageFactory<T> {

    public static TraceLogMessage getTraceLogMessage(TraceMessage traceMessage, String appName, String env, long time) {
        TraceLogMessage traceLogMessage = new TraceLogMessage();
        traceLogMessage.setAppName(appName);
        traceLogMessage.setEnv(env);
        traceLogMessage.setTraceId(traceMessage.getTraceId());
        traceLogMessage.setMethod(traceMessage.getMessageType());
        traceLogMessage.setTime(time);
        traceLogMessage.setPosition(traceMessage.getPosition());
        traceLogMessage.setPositionNum(traceMessage.getPositionNum().get());
        traceLogMessage.setServerName(IpGetter.CURRENT_IP);
        traceLogMessage.setTraceId(TraceId.logTraceID.get());
        return traceLogMessage;
    }

    public static RunLogMessage getLogMessage(String appName, String env, String message, long time) {
        RunLogMessage logMessage = new RunLogMessage();
        logMessage.setServerName(IpGetter.CURRENT_IP);
        logMessage.setAppName(appName);
        logMessage.setEnv(env);
        logMessage.setContent(message);
        logMessage.setDtTime(time);
        logMessage.setTraceId(TraceId.logTraceID.get());
        return logMessage;
    }

    public static String packageMessage(String message, Object[] args) {
        StringBuilder builder = new StringBuilder(128);
        builder.append(message);
        for (Object arg : args) {
            builder.append("\n").append(arg);
        }
        return builder.toString();
    }

}
