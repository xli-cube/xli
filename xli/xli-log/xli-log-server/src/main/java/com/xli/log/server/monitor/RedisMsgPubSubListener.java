package com.xli.log.server.monitor;

import com.xli.log.server.websocket.WebSocketSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * 利用redis的发布订阅，推送控制台消息
 *
 */

public class RedisMsgPubSubListener extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisMsgPubSubListener.class);
    @Override
    public void onMessage(String channel, String message) {
        WebSocketSession.sendToConsole(message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("开始订阅滚动日志！");
    }
}


