package com.xli.attach.frameattachstorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;

public interface IFrameAttachStorageService extends IService<FrameAttachStorage> {

    boolean insert(FrameAttachStorage frameAttachstorage);

    boolean delete(Long id);

    FrameAttachStorage find(Long id);
}
