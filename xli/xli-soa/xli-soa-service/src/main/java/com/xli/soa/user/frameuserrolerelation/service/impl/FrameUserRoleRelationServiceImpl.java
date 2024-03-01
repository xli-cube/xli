package com.xli.soa.user.frameuserrolerelation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.user.frameuserrolerelation.entity.FrameUserRoleRelation;
import com.xli.soa.user.frameuserrolerelation.mapper.FrameUserRoleRelationMapper;
import com.xli.soa.user.frameuserrolerelation.service.IFrameUserRoleRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameUserRoleRelationServiceImpl extends ServiceImpl<FrameUserRoleRelationMapper, FrameUserRoleRelation> implements IFrameUserRoleRelationService {

    @Override
    public boolean insert(FrameUserRoleRelation frameUserRoleRelation) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean update(FrameUserRoleRelation frameUserRoleRelation) {
        return false;
    }

    @Override
    public FrameUserRoleRelation find(Long id) {
        return null;
    }

    @Override
    public List<FrameUserRoleRelation> findList(QueryWrapper<FrameUserRoleRelation> qw) {
        return null;
    }

    @Override
    public long findCount(QueryWrapper<FrameUserRoleRelation> qw) {
        return 0;
    }

    @Override
    public Page<FrameUserRoleRelation> findList(QueryWrapper<FrameUserRoleRelation> qw, long current, long size) {
        return null;
    }
}
