package com.xli.attach.frameattachinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachinfo.mapper.FrameAttachInfoMapper;
import com.xli.attach.frameattachinfo.service.IFrameAttachInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrameAttachInfoServiceImpl extends ServiceImpl<FrameAttachInfoMapper, FrameAttachInfo> implements IFrameAttachInfoService {

    @Override
    public boolean insert(FrameAttachInfo frameAttachinfo) {
        return this.save(frameAttachinfo);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public FrameAttachInfo find(Long id) {
        return getById(id);
    }

    @Override
    public List<FrameAttachInfo> findByGroupId(Long groupId) {
        if (groupId != null) {
            QueryWrapper<FrameAttachInfo> qw = new QueryWrapper<>();
            qw.lambda().eq(FrameAttachInfo::getGroup_id, groupId);
            return this.list(qw);
        }
        return new ArrayList<>();
    }
}
