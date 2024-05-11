package com.xli.cache.support;

import com.xli.cache.monitor.CacheStat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StatInfo {

    private List<CacheStat> stats;

    private long startTime;

    private long endTime;

}
