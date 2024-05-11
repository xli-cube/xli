package com.xli.log.core;


import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class TraceMessage {

    private String traceId;

    private String messageType;

    private String position;

    private AtomicInteger positionNum = new AtomicInteger(0);
}
