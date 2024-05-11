package com.xli.log.server.monitor;

import com.xli.log.core.util.GfJsonUtil;
import com.xli.log.server.util.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class FeishuClient {

    public static void sendToFeishu(PlumeLogMonitorTextMessage plumeLogMonitorTextMessage, String URL) {
        Map<String, Object> requestBody = new HashMap<>(2);
        Map<String, Object> content = new HashMap<>(2);
        content.put("text", plumeLogMonitorTextMessage.getText());
        requestBody.put("msg_type", "text");
        requestBody.put("content", content);
        HttpClient.doPost(URL, GfJsonUtil.toJSONString(requestBody));
    }
}
