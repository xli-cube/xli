package com.xli.soa.role.framerolecategory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;
import com.xli.soa.role.framerolecategory.mapper.FrameRoleCategoryMapper;
import com.xli.soa.role.framerolecategory.service.IFrameRoleCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameRoleCategoryServiceImpl extends ServiceImpl<FrameRoleCategoryMapper, FrameRoleCategory>
        implements IFrameRoleCategoryService {

    @Override
    public boolean insert(FrameRoleCategory frameRoleCategory) {
        return this.save(frameRoleCategory);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameRoleCategory frameRoleCategory) {
        return this.update(frameRoleCategory);
    }

    @Override
    public FrameRoleCategory find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<FrameRoleCategory> findList(QueryWrapper<FrameRoleCategory> qw) {
        return this.list(qw);
    }

    @Override
    public long findCount(QueryWrapper<FrameRoleCategory> qw) {
        return this.count(qw);
    }

    @Override
    public Page<FrameRoleCategory> findList(QueryWrapper<FrameRoleCategory> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
