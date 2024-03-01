package com.xli.soa.role.framerolepermission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.role.framerolepermission.entity.FrameRolePermission;

import java.util.List;

public interface IFrameRolePermissionService extends IService<FrameRolePermission> {

    boolean insert(FrameRolePermission frameRolePermission);

    boolean delete(Long id);

    boolean update(FrameRolePermission frameRolePermission);

    FrameRolePermission find(Long id);

    List<FrameRolePermission> findList(QueryWrapper<FrameRolePermission> qw);

    long findCount(QueryWrapper<FrameRolePermission> qw);

    Page<FrameRolePermission> findList(QueryWrapper<FrameRolePermission> qw, long current, long size);
}
