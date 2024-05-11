package com.xli.log.server.collect;


import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.server.config.InitConfig;
import com.xli.log.server.client.ElasticLowerClient;
import com.xli.log.server.client.PlumeRestClient;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * className：RedisLogCollect
 * description：RedisLogCollect 获取rest接口中日志，存储到es
 *
 */
public class RestLogCollect extends BaseLogCollect {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RestLogCollect.class);
    private String restUserName = "";
    private String restPassWord = "";
    private String restUrl = "";

    public RestLogCollect(ElasticLowerClient elasticLowerClient, ApplicationEventPublisher applicationEventPublisher) {

        this.restUserName = InitConfig.restUserName;
        this.restPassWord = InitConfig.restPassWord;
        this.restUrl = InitConfig.restUrl;
        super.elasticLowerClient = elasticLowerClient;
        super.applicationEventPublisher = applicationEventPublisher;
        logger.info("restUrl:{}", restUrl);
    }

    public void restStart() {
        Thread runLogThread = startRunLogThread();
        Thread traceLogThread = startTraceLogThread();

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            Thread runLog = runLogThread;
            try {
                boolean runLogThreadAlive = runLog.isAlive();
                if (!runLogThreadAlive) {
                    throw new NullPointerException("runLogThread alive false");
                }
            } catch (Exception ex) {
                System.out.println("runLogThread 重启线程");
                runLog = startRunLogThread();
            }

            Thread traceLog = traceLogThread;
            try {
                boolean traceLogThreadAlive = traceLog.isAlive();
                if (!traceLogThreadAlive) {
                    throw new NullPointerException("traceLogThread alive false");
                }
            } catch (Exception ex) {
                logger.warn("traceLogThread 重启线程");
                traceLog = startTraceLogThread();
            }
        }, 10, 30, TimeUnit.SECONDS);

        logger.info("RestLogCollect is starting!");
    }

    private Thread startRunLogThread() {
        Thread runLogThread = new Thread(() -> collectRuningLog());
        runLogThread.start();
        return runLogThread;
    }

    private Thread startTraceLogThread() {
        Thread traceLogThread = new Thread(() -> collectTraceLog());
        traceLogThread.start();
        return traceLogThread;
    }

    private void collectRuningLog() {
        while (true) {
            List<String> logs = new ArrayList<>();

            try {
                Thread.sleep(InitConfig.MAX_INTERVAL);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            try {
                logs = PlumeRestClient.getLogs(this.restUrl + "?maxSendSize=" + InitConfig.MAX_SEND_SIZE + "&logKey=" + LogMessageConstant.LOG_KEY, this.restUserName, this.restPassWord);
                if (logger.isDebugEnabled()) {
                    logs.forEach(log -> {
                        logger.debug(log);
                    });
                }
            } catch (Exception e) {
                logger.error("从plumelog-server拉取日志失败！", e);
            }
            //发布一个事件
            super.sendLog(super.getRunLogIndex(), logs);
            publisherMonitorEvent(logs);
        }
    }

    private void collectTraceLog() {
        while (true) {
            List<String> logs = new ArrayList<>();
            try {
                Thread.sleep(InitConfig.MAX_INTERVAL);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            try {
                logs = PlumeRestClient.getLogs(this.restUrl + "?maxSendSize=" + InitConfig.MAX_SEND_SIZE + "&logKey=" + LogMessageConstant.LOG_KEY_TRACE, this.restUserName, this.restPassWord);
                if (logger.isDebugEnabled()) {
                    logs.forEach(log -> {
                        logger.debug(log);
                    });
                }
            } catch (Exception e) {
                logger.error("从plumelog-server队列拉取日志失败！", e);
            }
            super.sendLog(super.getTraceLogIndex(), logs);
        }
    }

}
