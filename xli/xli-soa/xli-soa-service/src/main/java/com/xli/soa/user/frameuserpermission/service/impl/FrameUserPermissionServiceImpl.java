package com.xli.soa.user.frameuserpermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.user.frameuserpermission.entity.FrameUserPermission;
import com.xli.soa.user.frameuserpermission.mapper.FrameUserPermissionMapper;
import com.xli.soa.user.frameuserpermission.service.IFrameUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FrameUserPermissionServiceImpl extends ServiceImpl<FrameUserPermissionMapper, FrameUserPermission> implements IFrameUserPermissionService {


    @Override
    public boolean insert(FrameUserPermission frameUserPermission) {
        return this.save(frameUserPermission);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameUserPermission frameUserPermission) {
        return this.updateById(frameUserPermission);
    }

    @Override
    public FrameUserPermission find(Long id) {
        return this.getById(id);
    }

    @Override
    public long findCount(QueryWrapper<FrameUserPermission> qw) {
        return this.count(qw);
    }

    @Override
    public List<FrameUserPermission> findList(QueryWrapper<FrameUserPermission> qw) {
        return this.list(qw);
    }

    @Override
    public Page<FrameUserPermission> findList(QueryWrapper<FrameUserPermission> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public List<FrameUserPermission> findPermissionListByUserId(Long userId) {
        return null;
    }
}
