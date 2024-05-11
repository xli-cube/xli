package com.xli.log.core.kafka;

import com.xli.log.core.client.AbstractClient;
import com.xli.log.core.exception.LogQueueConnectException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

public class KafkaProducerClient extends AbstractClient {
    private static KafkaProducerClient instance;
    private KafkaProducerPool kafkaProducerPool;

    private KafkaProducerClient(String hosts, String compressionType) {
        this.kafkaProducerPool = new KafkaProducerPool(hosts, compressionType);
    }

    public static KafkaProducerClient getInstance(String hosts, String compressionType) {
        if (instance == null) {
            synchronized (KafkaProducerClient.class) {
                if (instance == null) {
                    instance = new KafkaProducerClient(hosts, compressionType);
                }
            }
        }
        return instance;
    }

    @Override
    public void pushMessage(String topic, String message) throws LogQueueConnectException {
        KafkaProducer kafkaProducer = null;
        try {
            kafkaProducer = kafkaProducerPool.getResource();
            kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
        } catch (Exception e) {
            throw new LogQueueConnectException("kafka 写入失败！", e);
        } finally {
            if (kafkaProducer != null) {
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }

    }

    @Override
    public void putMessageList(String topic, List<String> list) throws LogQueueConnectException {
        KafkaProducer kafkaProducer = null;
        try {
            kafkaProducer = kafkaProducerPool.getResource();
            for (int a = 0; a < list.size(); a++) {
                String message = list.get(a);
                kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
            }
        } catch (Exception e) {
            throw new LogQueueConnectException("kafka 写入失败！", e);
        } finally {
            if (kafkaProducer != null) {
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }

    }
}
