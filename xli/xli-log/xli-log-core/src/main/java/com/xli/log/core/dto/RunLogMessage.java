package com.xli.log.core.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RunLogMessage extends BaseLogMessage {

    private Long dtTime;
    private String content;
    private String logLevel;
    private String className;
    private String threadName;
    private String logType;
    private String dateTime;
    /**
     * 当dtTime相同时服务端无法正确排序，因此需要增加一个字段保证相同毫秒的日志可正确排序
     */
    private Long seq;

}
