package com.xli.log.server.config;

import com.xli.log.core.client.AbstractClient;
import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.core.client.AbstractServerClient;
import com.xli.log.core.lucene.LuceneClient;
import com.xli.log.server.client.ElasticLowerClient;
import com.xli.log.server.collect.KafkaLogCollect;
import com.xli.log.server.collect.LocalLogCollect;
import com.xli.log.server.collect.RedisLogCollect;
import com.xli.log.server.collect.RestLogCollect;
import com.xli.log.server.monitor.RedisMsgPubSubListener;
import com.xli.log.server.util.IndexUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;
import javax.annotation.PreDestroy;

/**
 * className：CollectStartBean
 * description：日誌搜集spring bean
 */
@Component
@Order(100)
public class CollectStartBean implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CollectStartBean.class);

    @Autowired
    private AbstractServerClient abstractServerClient;

    @Autowired(required = false)
    private AbstractClient redisQueueClient;

    @Autowired(required = false)
    private AbstractClient redisClient;

    @Autowired(required = false)
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired(required = false)
    @Value("${plumelog.redis.compressor:false}")
    private Boolean compressor;
    @Autowired(required = false)
    @Value("${plumelog.inside.redis.host:}")
    private String insideRedis;

    private RedisServer redisServer;


    private void serverStart() {

        if (!StringUtils.isEmpty(insideRedis)) {
            String[] hs = insideRedis.split(":");
            redisServer = RedisServer.builder()
                    .port(Integer.parseInt(hs[1]))
                    .setting("bind " + hs[0]) //绑定ip
                    .build();
            redisServer.start();
            logger.info("inside redis start!");
        }

        if (InitConfig.KAFKA_MODE_NAME.equals(InitConfig.START_MODEL)) {
            KafkaLogCollect kafkaLogCollect = new KafkaLogCollect((ElasticLowerClient) abstractServerClient, kafkaConsumer,
                    applicationEventPublisher, redisClient);
            kafkaLogCollect.kafkaStart();
        }
        if (InitConfig.REDIS_MODE_NAME.equals(InitConfig.START_MODEL) || InitConfig.REDIS_SENTINEL_MODE_NAME
                .equals(InitConfig.START_MODEL) || InitConfig.REDIS_CLUSTER_MODE_NAME.equals(InitConfig.START_MODEL)) {
            RedisLogCollect redisLogCollect = new RedisLogCollect((ElasticLowerClient) abstractServerClient, redisQueueClient,
                    applicationEventPublisher, compressor, redisClient);
            redisLogCollect.redisStart();
        }
        if (InitConfig.REST_MODE_NAME.equals(InitConfig.START_MODEL)) {
            RestLogCollect restLogCollect = new RestLogCollect((ElasticLowerClient) abstractServerClient, applicationEventPublisher);
            restLogCollect.restStart();
        }
        if (InitConfig.LITE_MODE_NAME.equals(InitConfig.START_MODEL)) {
            LocalLogCollect localLogCollect = new LocalLogCollect((LuceneClient) abstractServerClient);
            localLogCollect.start();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        RedisMsgPubSubListener sp = new RedisMsgPubSubListener();
                        redisClient.subscribe(sp, InitConfig.WEB_CONSOLE_CHANNEL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    @Override
    public void afterPropertiesSet() {
        try {
            if (InitConfig.ES_INDEX_MODEL.equals("hour") && !InitConfig.START_MODEL.equals(InitConfig.LITE_MODE_NAME)) {
                abstractServerClient.addShards(InitConfig.maxShards);
                logger.info("set es max_shards_per_node of :" + InitConfig.maxShards);
            }
            if (!InitConfig.START_MODEL.equals(InitConfig.LITE_MODE_NAME)) {
                autoCreatIndice();
            }
            serverStart();
        } catch (Exception e) {
            logger.error("plumelog server starting failed!", e);
        }
    }

    private void autoCreatIndice() {
        long epochMillis = System.currentTimeMillis();
        String runLogIndex = IndexUtil.getRunLogIndex(epochMillis);
        String traceLogIndex = IndexUtil.getTraceLogIndex(epochMillis);
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


    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
