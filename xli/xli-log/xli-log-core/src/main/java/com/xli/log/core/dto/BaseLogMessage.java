package com.xli.log.core.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseLogMessage {

    /**
     * 记录服务IP
     */
    private String serverName;

    /**
     * 追踪码
     */
    private String traceId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用环境
     */
    private String env;

    /**
     * 方法名
     */
    private String method;

    public String getAppNameWithEnv() {
        return this.appName + "-_-" + this.env;
    }
}
