package com.xli.soa.module.framemodulepermission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.module.framemodulepermission.entity.FrameModulePermission;

import java.util.List;

public interface IFrameModulePermissionService extends IService<FrameModulePermission> {

    boolean insert(FrameModulePermission frameModulePermission);

    boolean delete(Long id);

    boolean update(FrameModulePermission frameModulePermission);

    FrameModulePermission find(Long id);

    List<FrameModulePermission> findList(QueryWrapper<FrameModulePermission> qw);

    long findCount(QueryWrapper<FrameModulePermission> qw);

    Page<FrameModulePermission> findList(QueryWrapper<FrameModulePermission> qw, long current, long size);
}
