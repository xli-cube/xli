package com.xli.log.trace.aspect;

import com.xli.log.core.LogMessageThreadLocal;
import com.xli.log.core.TraceId;
import com.xli.log.core.TraceMessage;
import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.core.util.GfJsonUtil;
import com.xli.log.core.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.LoggerFactory;

/**
 * className：AbstractAspect
 * description： 链路追踪打点拦截
 */
public abstract class AbstractAspect {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(AbstractAspect.class);

    public Object aroundExecute(JoinPoint joinPoint) throws Throwable {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String traceId = TraceId.logTraceID.get();
        if (traceMessage == null) {
            traceMessage = new TraceMessage();
            traceMessage.getPositionNum().set(0);
        }
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(LogMessageConstant.TRACE_START);
        traceMessage.getPositionNum().incrementAndGet();
        LogMessageThreadLocal.logMessageThreadLocal.set(traceMessage);
        if (traceId != null) {
            log.info(LogMessageConstant.TRACE_PRE + GfJsonUtil.toJSONString(traceMessage));
        }
        Object proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(LogMessageConstant.TRACE_END);
        traceMessage.getPositionNum().incrementAndGet();
        if (traceId != null) {
            log.info(LogMessageConstant.TRACE_PRE + GfJsonUtil.toJSONString(traceMessage));
        }
        return proceed;
    }
}
