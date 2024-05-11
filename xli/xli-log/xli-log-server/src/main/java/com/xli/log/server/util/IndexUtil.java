package com.xli.log.server.util;

import com.xli.log.core.constant.LogMessageConstant;
import com.xli.log.server.config.InitConfig;

public class IndexUtil {

    public static String getRunLogIndex(long epochMillis) {
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_"
                + DateUtil.getDateShortStr(InitConfig.ES_INDEX_ZONE_ID, epochMillis);
    }

    public static String getTraceLogIndex(long epochMillis) {
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_"
                + DateUtil.getDateShortStr(InitConfig.ES_INDEX_ZONE_ID, epochMillis);
    }

    public static String getRunLogIndexWithHour(long epochMillis) {
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_"
                + DateUtil.getDateWithHourShortStr(InitConfig.ES_INDEX_ZONE_ID, epochMillis);
    }

    public static String getTraceLogIndexWithHour(long epochMillis) {
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_"
                + DateUtil.getDateWithHourShortStr(InitConfig.ES_INDEX_ZONE_ID, epochMillis);
    }

}
