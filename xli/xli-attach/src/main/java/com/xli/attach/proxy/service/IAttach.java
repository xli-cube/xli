package com.xli.attach.proxy.service;

import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;

public interface IAttach {

    boolean insert(FrameAttachStorage frameAttachStorage);

    boolean delete(FrameAttachInfo frameAttachInfo);

    FrameAttachStorage getAttach(FrameAttachInfo frameAttachInfo);
}
