package com.xli.log.core.exception;

/**
 * className：LogQueueConnectException
 * description：连接日志队列异常抛出，redis挂了，或者kafk挂了d
 *
 */
public class LogQueueConnectException extends Exception {

    public LogQueueConnectException() {
        super();
    }

    public LogQueueConnectException(String message) {
        super(message);
    }

    public LogQueueConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogQueueConnectException(Throwable cause) {
        super(cause);
    }
}
