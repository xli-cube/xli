package com.xli.attach.proxy.factory.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;
import com.xli.attach.frameattachstorage.service.IFrameAttachStorageService;
import com.xli.attach.proxy.service.IAttach;

public class AttachForDBImpl implements IAttach {

    @Override
    public boolean insert(FrameAttachStorage frameAttachStorage) {
        IFrameAttachStorageService iFrameAttachStorageService = SpringUtil.getBean(IFrameAttachStorageService.class);
        return iFrameAttachStorageService.insert(frameAttachStorage);
    }

    @Override
    public boolean delete(FrameAttachInfo frameAttachInfo) {
        IFrameAttachStorageService iFrameAttachStorageService = SpringUtil.getBean(IFrameAttachStorageService.class);
        return iFrameAttachStorageService.delete(frameAttachInfo.getId());
    }

    @Override
    public FrameAttachStorage getAttach(FrameAttachInfo frameAttachInfo) {
        IFrameAttachStorageService iFrameAttachStorageService = SpringUtil.getBean(IFrameAttachStorageService.class);
        return iFrameAttachStorageService.find(frameAttachInfo.getId());
    }
}
