package com.xli.log.server.monitor;

import com.xli.log.core.dto.RunLogMessage;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * className：PlumelogMonitorEvent
 * description： 日志报警事件
 */

public class PlumelogMonitorEvent extends ApplicationEvent {

    /**
     * 日志信息列表
     */
    List<RunLogMessage> logs;


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PlumelogMonitorEvent(Object source, List<RunLogMessage> logs) {
        super(source);
        this.logs = logs;
    }

    public List<RunLogMessage> getLogs() {
        return logs;
    }

}
