package com.xli.log.server.controller;

import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.server.config.InitConfig;
import com.xli.log.core.client.AbstractServerClient;
import com.xli.log.server.util.IndexUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * className：AutoDeleteLogs
 * description：自动删除日志，凌晨0点执行
 */
@Component
public class AutoDeleteLogs {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AutoDeleteLogs.class);

    @Autowired
    private AbstractServerClient abstractServerClient;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteLogs() {
        if (InitConfig.keepDays > 0) {
            try {
                logger.info("begin delete {} days ago run logs!", InitConfig.keepDays);
                String runLogIndex = IndexUtil.getRunLogIndex(
                        System.currentTimeMillis() - InitConfig.keepDays * InitConfig.MILLS_ONE_DAY);
                abstractServerClient.deleteIndex(runLogIndex);
                for (int a = 0; a < 24; a++) {
                    String hour = String.format("%02d", a);
                    abstractServerClient.deleteIndex(runLogIndex + hour);
                }
                logger.info("delete success! index:" + runLogIndex);
            } catch (Exception e) {
                logger.error("delete logs error!", e);
            }
        } else {
            logger.info("unwanted delete logs");
        }
        if (InitConfig.traceKeepDays > 0) {
            try {
                logger.info("begin delete {} days ago trace logs!", InitConfig.traceKeepDays);
                String traceLogIndex = IndexUtil.getTraceLogIndex(
                        System.currentTimeMillis() - InitConfig.traceKeepDays * InitConfig.MILLS_ONE_DAY);
                abstractServerClient.deleteIndex(traceLogIndex);
                for (int a = 0; a < 24; a++) {
                    String hour = String.format("%02d", a);
                    abstractServerClient.deleteIndex(traceLogIndex + hour);
                }
                logger.info("delete success! index:" + traceLogIndex);
            } catch (Exception e) {
                logger.error("delete logs error!", e);
            }
        } else {
            logger.info("unwanted delete logs");
        }
    }

    /**
     * 每隔一个小时自动创建未来24小时的索引
     * 由于无法确定指定的是哪个时区，因此每个小时都执行一次
     */
    @Scheduled(cron = "0 30 * * * ?")
    public void createIndice() {
        long time = System.currentTimeMillis() + InitConfig.MILLS_ONE_DAY;
        String runLogIndex = IndexUtil.getRunLogIndex(time);
        String traceLogIndex = IndexUtil.getTraceLogIndex(time);
        if ("day".equals(InitConfig.ES_INDEX_MODEL)) {
            creatIndiceLog(runLogIndex);
            creatIndiceTrace(traceLogIndex);
        } else {
            for (int a = 0; a < 24; a++) {
                String hour = String.format("%02d", a);
                creatIndiceLog(runLogIndex + hour);
                creatIndiceTrace(traceLogIndex + hour);
            }
        }
    }

    private void creatIndiceLog(String index) {
        if (!abstractServerClient.existIndice(index)) {
            abstractServerClient.creatIndice(index, LogMessageConstant.ES_TYPE);
        }
    }

    private void creatIndiceTrace(String index) {
        if (!abstractServerClient.existIndice(index)) {
            abstractServerClient.creatIndiceTrace(index, LogMessageConstant.ES_TYPE);
        }
    }
}
