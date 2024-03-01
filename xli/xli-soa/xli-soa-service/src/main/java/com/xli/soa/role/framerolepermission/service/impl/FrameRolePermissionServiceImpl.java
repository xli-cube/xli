package com.xli.soa.role.framerolepermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.role.framerolepermission.entity.FrameRolePermission;
import com.xli.soa.role.framerolepermission.mapper.FrameRolePermissionMapper;
import com.xli.soa.role.framerolepermission.service.IFrameRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameRolePermissionServiceImpl extends ServiceImpl<FrameRolePermissionMapper, FrameRolePermission> implements IFrameRolePermissionService {

    @Override
    public boolean insert(FrameRolePermission frameRolePermission) {
        return this.save(frameRolePermission);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameRolePermission frameRolePermission) {
        return false;
    }

    @Override
    public FrameRolePermission find(Long id) {
        return null;
    }

    @Override
    public List<FrameRolePermission> findList(QueryWrapper<FrameRolePermission> qw) {
        return null;
    }

    @Override
    public long findCount(QueryWrapper<FrameRolePermission> qw) {
        return 0;
    }

    @Override
    public Page<FrameRolePermission> findList(QueryWrapper<FrameRolePermission> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
