package com.xli.attach.service;

import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;

import java.io.InputStream;
import java.util.List;

public interface IAttachService {

    boolean insert(FrameAttachInfo frameAttachinfo, InputStream content);

    boolean delete(Long id);

    FrameAttachInfo getAttach(Long id);

    FrameAttachInfo getAttachStorage(Long id);

    List<FrameAttachInfo> getAttachListByGroupId(Long groupId);

    List<FrameAttachInfo> getAttachStorageByGroupId(Long groupId);
}
