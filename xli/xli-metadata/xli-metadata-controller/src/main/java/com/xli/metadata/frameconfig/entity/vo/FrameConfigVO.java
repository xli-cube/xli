package com.xli.metadata.frameconfig.entity.vo;

import lombok.Data;

@Data
public class FrameConfigVO {

    private Long id;

    private String configName;

    private String configValue;

    private String description;

    private Long categoryId;

    private Integer orderNum;
}
