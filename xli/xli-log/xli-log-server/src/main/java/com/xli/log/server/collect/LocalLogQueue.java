package com.xli.log.server.collect;

import com.xli.log.core.dto.RunLogMessage;
import com.xli.log.core.dto.TraceLogMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LocalLogQueue{
        public static BlockingQueue<RunLogMessage> rundataQueue=new LinkedBlockingQueue<>(1000000);
        public static BlockingQueue<TraceLogMessage> tracedataQueue=new LinkedBlockingQueue<>(1000000);
}
