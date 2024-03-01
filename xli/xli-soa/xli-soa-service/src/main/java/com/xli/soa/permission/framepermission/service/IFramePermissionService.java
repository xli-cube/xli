package com.xli.soa.permission.framepermission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.permission.framepermission.entity.FramePermission;

import java.util.List;

public interface IFramePermissionService extends IService<FramePermission> {

    boolean insert(FramePermission framePermission);

    boolean delete(Long id);

    boolean update(FramePermission framePermission);

    FramePermission find(Long id);

    long findCount(QueryWrapper<FramePermission> qw);

    List<FramePermission> findList(QueryWrapper<FramePermission> qw);

    Page<FramePermission> findList(QueryWrapper<FramePermission> qw, long current, long size);
}
