package com.xli.log.core;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogMessage {

    /**
     * 应用名称用来区分日志属于哪个应用
     */
    private String appName;

    /**
     * 应用环境用来区分日志属于哪个应用环境
     */
    private String env;

    /**
     * 应用运行所属IP地址
     */
    private String serverName;
    private Long dtTime;
    private Long seq;

    /**
     * 应用traceId，配置了拦截器才能自动生成
     */
    private String traceId;
    private String content;
    private String logLevel;
    private String threadName;
    private String className;
    private String method;

    /**
     * 日志类型，区分运行日志还是链路日志
     */
    private String logType;
    private String dateTime;

}
