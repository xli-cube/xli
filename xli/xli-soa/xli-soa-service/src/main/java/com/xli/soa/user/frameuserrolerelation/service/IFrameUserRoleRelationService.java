package com.xli.soa.user.frameuserrolerelation.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.user.frameuserrolerelation.entity.FrameUserRoleRelation;

import java.util.List;

public interface IFrameUserRoleRelationService extends IService<FrameUserRoleRelation> {

    boolean insert(FrameUserRoleRelation frameUserRoleRelation);

    boolean delete(Long id);

    boolean update(FrameUserRoleRelation frameUserRoleRelation);

    FrameUserRoleRelation find(Long id);

    List<FrameUserRoleRelation> findList(QueryWrapper<FrameUserRoleRelation> qw);

    long findCount(QueryWrapper<FrameUserRoleRelation> qw);

    Page<FrameUserRoleRelation> findList(QueryWrapper<FrameUserRoleRelation> qw, long current, long size);
}
