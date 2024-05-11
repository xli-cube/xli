package com.xli.log.core.kafka;

import com.xli.log.core.util.Pool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerPool extends Pool<KafkaProducer> {

    public KafkaProducerPool(final GenericObjectPoolConfig poolConfig, final String hosts, final String compressionType) {
        super(poolConfig, new KafkaProducerFactory(hosts, compressionType));
    }

    public KafkaProducerPool(final String hosts, final String compressionType) {
        super(new GenericObjectPoolConfig(), new KafkaProducerFactory(hosts, compressionType));
    }

    @Override
    public KafkaProducer getResource() {
        KafkaProducer connection = super.getResource();
        return connection;
    }

    @Override
    public void returnBrokenResource(final KafkaProducer resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    @Override
    public void returnResource(final KafkaProducer resource) {
        if (resource != null) {
            try {
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
            }
        }
    }

}
