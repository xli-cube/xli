package com.xli.soa.role.framerole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.role.framerole.entity.FrameRole;
import com.xli.soa.role.framerole.mapper.FrameRoleMapper;
import com.xli.soa.role.framerole.service.IFrameRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrameRoleServiceImpl extends ServiceImpl<FrameRoleMapper, FrameRole> implements IFrameRoleService {

    @Override
    public boolean insert(FrameRole frameRole) {
        return this.save(frameRole);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameRole frameRole) {
        return false;
    }

    @Override
    public FrameRole find(Long id) {
        return null;
    }

    @Override
    public List<FrameRole> findList(QueryWrapper<FrameRole> qw) {
        return null;
    }

    @Override
    public long findCount(QueryWrapper<FrameRole> qw) {
        return 0;
    }

    @Override
    public Page<FrameRole> findList(QueryWrapper<FrameRole> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public List<FrameRole> findRoleListByUserId(Long id) {
        List<FrameRole> roleList = new ArrayList<>();
        return roleList;
    }
}
