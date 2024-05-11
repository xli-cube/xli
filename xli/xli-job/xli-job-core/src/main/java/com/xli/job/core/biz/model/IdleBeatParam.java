package com.xli.job.core.biz.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class IdleBeatParam implements Serializable {
    private static final long serialVersionUID = 42L;

    public IdleBeatParam() {
    }
    public IdleBeatParam(int jobId) {
        this.jobId = jobId;
    }

    private int jobId;


}