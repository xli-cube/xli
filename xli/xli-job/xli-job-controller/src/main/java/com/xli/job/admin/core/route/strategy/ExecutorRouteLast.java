package com.xli.job.admin.core.route.strategy;

import com.xli.job.admin.core.route.ExecutorRouter;
import com.xli.job.core.biz.model.ReturnT;
import com.xli.job.core.biz.model.TriggerParam;

import java.util.List;

public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}
