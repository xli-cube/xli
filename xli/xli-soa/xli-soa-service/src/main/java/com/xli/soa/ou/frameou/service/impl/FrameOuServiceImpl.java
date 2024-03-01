package com.xli.soa.ou.frameou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.ou.frameou.entity.FrameOu;
import com.xli.soa.ou.frameou.mapper.FrameOuMapper;
import com.xli.soa.ou.frameou.service.IFrameOuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FrameOuServiceImpl extends ServiceImpl<FrameOuMapper, FrameOu> implements IFrameOuService {

    @Override
    public boolean insert(FrameOu frameOu) {
        return this.save(frameOu);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameOu frameOu) {
        return this.updateById(frameOu);
    }

    @Override
    public FrameOu find(Long id) {
        return this.getById(id);
    }

    @Override
    public long findCount(QueryWrapper<FrameOu> qw) {
        return this.count(qw);
    }

    @Override
    public List<FrameOu> findList(QueryWrapper<FrameOu> qw) {
        return this.list(qw);
    }

    @Override
    public Page<FrameOu> findList(QueryWrapper<FrameOu> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
