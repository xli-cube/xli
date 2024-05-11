package com.xli.log.server.collect;

import com.xli.log.core.client.AbstractClient;
import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.server.client.ElasticLowerClient;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * className：KafkaLogCollect
 * description：KafkaLogCollect 获取kafka中日志，存储到es
 *
 */
public class KafkaLogCollect extends BaseLogCollect {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaLogCollect.class);
    private AbstractClient redisClient;
    private KafkaConsumer<String, String> kafkaConsumer;

    public KafkaLogCollect(ElasticLowerClient elasticLowerClient, KafkaConsumer kafkaConsumer, ApplicationEventPublisher applicationEventPublisher,AbstractClient redisClient) {
        super.elasticLowerClient = elasticLowerClient;
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaConsumer.subscribe(Arrays.asList(LogMessageConstant.LOG_KEY, LogMessageConstant.LOG_KEY_TRACE));
        super.applicationEventPublisher = applicationEventPublisher;
        super.redisClient=redisClient;
        logger.info("kafkaConsumer subscribe ready!");
        logger.info("sending log ready!");
    }

    public void kafkaStart() {
        threadPoolExecutor.execute(() -> {
            collectRuningLog();
        });
        logger.info("KafkaLogCollect is starting!");
    }

    public void collectRuningLog() {
        while (true) {
            List<String> logList = new ArrayList();
            List<String> sendlogList = new ArrayList();
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                records.forEach(record -> {
                    if (logger.isDebugEnabled()) {
                        logger.debug("get log:" + record.value() + "  logType:" + record.topic());
                    }
                    if (record.topic().equals(LogMessageConstant.LOG_KEY)) {
                        logList.add(record.value());
                    }
                    if (record.topic().equals(LogMessageConstant.LOG_KEY_TRACE)) {
                        sendlogList.add(record.value());
                    }
                });
            } catch (Exception e) {
                logger.error("get logs from kafka failed! ", e);
            }
            if (logList.size() > 0) {
                super.sendLog(super.getRunLogIndex(), logList);

                publisherMonitorEvent(logList);
            }
            if (sendlogList.size() > 0) {
                super.sendTraceLogList(super.getTraceLogIndex(), sendlogList);
            }
        }
    }
}
