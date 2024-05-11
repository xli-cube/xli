package com.xli.job.admin.core.alarm;

import com.xli.job.admin.core.model.XxlJobInfo;
import com.xli.job.admin.core.model.XxlJobLog;

public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
