package com.xli.attach.frameattachstorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;
import com.xli.attach.frameattachstorage.mapper.FrameAttachStorageMapper;
import com.xli.attach.frameattachstorage.service.IFrameAttachStorageService;
import org.springframework.stereotype.Service;

@Service
public class FrameAttachStorageServiceImpl extends ServiceImpl<FrameAttachStorageMapper, FrameAttachStorage> implements IFrameAttachStorageService {

    @Override
    public boolean insert(FrameAttachStorage frameAttachstorage) {
        return this.save(frameAttachstorage);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public FrameAttachStorage find(Long id) {
        return this.getById(id);
    }
}
