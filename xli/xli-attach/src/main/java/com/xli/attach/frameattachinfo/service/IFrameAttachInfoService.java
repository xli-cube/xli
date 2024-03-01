package com.xli.attach.frameattachinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;

import java.util.List;

public interface IFrameAttachInfoService extends IService<FrameAttachInfo> {

    boolean insert(FrameAttachInfo frameAttachinfo);

    boolean delete(Long id);

    FrameAttachInfo find(Long id);

    List<FrameAttachInfo> findByGroupId(Long groupId);
}
