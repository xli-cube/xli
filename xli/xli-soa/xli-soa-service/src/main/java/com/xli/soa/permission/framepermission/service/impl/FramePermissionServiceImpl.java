package com.xli.soa.permission.framepermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.permission.framepermission.entity.FramePermission;
import com.xli.soa.permission.framepermission.mapper.FramePermissionMapper;
import com.xli.soa.permission.framepermission.service.IFramePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FramePermissionServiceImpl extends ServiceImpl<FramePermissionMapper, FramePermission> implements IFramePermissionService {


    @Override
    public boolean insert(FramePermission frameUser) {
        return this.save(frameUser);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FramePermission frameUser) {
        return this.updateById(frameUser);
    }

    @Override
    public FramePermission find(Long id) {
        return this.getById(id);
    }

    @Override
    public long findCount(QueryWrapper<FramePermission> qw) {
        return this.count(qw);
    }

    @Override
    public List<FramePermission> findList(QueryWrapper<FramePermission> qw) {
        return this.list(qw);
    }

    @Override
    public Page<FramePermission> findList(QueryWrapper<FramePermission> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
