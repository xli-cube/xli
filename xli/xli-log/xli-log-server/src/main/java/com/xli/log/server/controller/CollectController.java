package com.xli.log.server.controller;

import com.xli.log.core.dto.RunLogMessage;
import com.xli.log.core.dto.TraceLogMessage;
import com.xli.log.core.util.GfJsonUtil;
import com.xli.log.server.config.InitConfig;
import com.xli.log.core.client.AbstractServerClient;
import com.xli.log.server.collect.LocalLogQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * className：CollectController
 * description：lite模式下日志推送接口
 *
 */
@RestController
@CrossOrigin
public class CollectController {

    private final Logger logger = LoggerFactory.getLogger(CollectController.class);

    @Autowired
    private AbstractServerClient abstractServerClient;

    @RequestMapping({"/sendRunLog", "/plumelogServer/sendRunLog"})
    public Result sendRunLog(@RequestBody String logs) {
        Result result = new Result();
        if ("lite".equals(InitConfig.START_MODEL)) {
            try {
                List<RunLogMessage> list= GfJsonUtil.parseArray(logs,RunLogMessage.class);
                LocalLogQueue.rundataQueue.addAll(list);
                logger.info("receive runLog messages! count:{}",list.size());
            } catch (Exception e) {
                result.setCode(500);
                result.setMessage("send logs error! :" + e.getMessage());
            }
            result.setCode(200);
            result.setMessage("send logs! success");
        } else {
            result.setCode(500);
            result.setMessage("send logs error! rest model only support redis model");
        }
        return result;
    }

    @RequestMapping({"/sendTraceLog", "/plumelogServer/sendTraceLog"})
    public Result sendTraceLog(@RequestBody String logs) {
        Result result = new Result();
        if ("lite".equals(InitConfig.START_MODEL)) {
            try {
                List<TraceLogMessage> list=GfJsonUtil.parseArray(logs,TraceLogMessage.class);
                LocalLogQueue.tracedataQueue.addAll(list);
                logger.info("receive traceLog messages! count:{}",list.size());
            } catch (Exception e) {
                result.setCode(500);
                result.setMessage("send logs error! :" + e.getMessage());
            }
            result.setCode(200);
            result.setMessage("send logs! success");
        } else {
            result.setCode(500);
            result.setMessage("send logs error! rest model only support redis model");
        }
        return result;
    }
}
