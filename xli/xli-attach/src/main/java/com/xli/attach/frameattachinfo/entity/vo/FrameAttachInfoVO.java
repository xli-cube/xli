package com.xli.attach.frameattachinfo.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FrameAttachInfoVO implements Serializable {

    private String id;
    private String fileName;
    private String fileType;
    private String groupId;
    private String fileSize;
    private String hashCode;
    private String orderNum;
    private String url;
}
