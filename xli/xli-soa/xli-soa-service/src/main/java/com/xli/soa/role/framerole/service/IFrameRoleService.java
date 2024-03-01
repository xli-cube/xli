package com.xli.soa.role.framerole.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.role.framerole.entity.FrameRole;

import java.util.List;

public interface IFrameRoleService extends IService<FrameRole> {

    boolean insert(FrameRole frameRole);

    boolean delete(Long id);

    boolean update(FrameRole frameRole);

    FrameRole find(Long id);

    List<FrameRole> findList(QueryWrapper<FrameRole> qw);

    long findCount(QueryWrapper<FrameRole> qw);

    Page<FrameRole> findList(QueryWrapper<FrameRole> qw, long current, long size);

    List<FrameRole> findRoleListByUserId(Long id);
}
