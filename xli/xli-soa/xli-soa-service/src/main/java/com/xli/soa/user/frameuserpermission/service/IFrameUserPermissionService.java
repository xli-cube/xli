package com.xli.soa.user.frameuserpermission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.user.frameuser.entity.FrameUser;
import com.xli.soa.user.frameuserpermission.entity.FrameUserPermission;

import java.util.List;

public interface IFrameUserPermissionService extends IService<FrameUserPermission> {

    boolean insert(FrameUserPermission frameUserPermission);

    boolean delete(Long id);

    boolean update(FrameUserPermission frameUser);

    FrameUserPermission find(Long id);

    long findCount(QueryWrapper<FrameUserPermission> qw);

    List<FrameUserPermission> findList(QueryWrapper<FrameUserPermission> qw);

    Page<FrameUserPermission> findList(QueryWrapper<FrameUserPermission> qw, long current, long size);

    List<FrameUserPermission> findPermissionListByUserId(Long userId);
}
