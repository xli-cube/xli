package com.xli.cache.anno.support;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Setter
@Getter
public class PenetrationProtectConfig {
    private boolean penetrationProtect;
    private Duration penetrationProtectTimeout;

}
