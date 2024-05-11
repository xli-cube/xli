package com.xli.log.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * className：WarningRule
 * description：错误告警规则
 * time：2020/7/2  10:52
 *
 */
@Setter
@Getter
public class WarningRule {

    private String appName;
    private String env;
    private String appCategory;
    private String className;
    private String receiver;
    private String webhookUrl;
    private int errorCount;
    private int time;
    private int status;
    private int hookServe = 1; // 1 DingTalk 2 Wechat

}
