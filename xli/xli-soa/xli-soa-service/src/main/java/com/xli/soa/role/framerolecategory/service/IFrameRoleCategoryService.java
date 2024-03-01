package com.xli.soa.role.framerolecategory.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;

import java.util.List;

public interface IFrameRoleCategoryService extends IService<FrameRoleCategory> {

    boolean insert(FrameRoleCategory frameRoleCategory);
    boolean delete(Long id);

    boolean update(FrameRoleCategory frameRoleCategory);

    FrameRoleCategory find(Long id);

    List<FrameRoleCategory> findList(QueryWrapper<FrameRoleCategory> qw);

    long findCount(QueryWrapper<FrameRoleCategory> qw);

    Page<FrameRoleCategory> findList(QueryWrapper<FrameRoleCategory> qw, long current, long size);
}
