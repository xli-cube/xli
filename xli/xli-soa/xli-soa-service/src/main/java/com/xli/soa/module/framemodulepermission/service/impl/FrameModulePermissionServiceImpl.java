package com.xli.soa.module.framemodulepermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.module.framemodulepermission.entity.FrameModulePermission;
import com.xli.soa.module.framemodulepermission.mapper.FrameModulePermissionMapper;
import com.xli.soa.module.framemodulepermission.service.IFrameModulePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户实现类
 *
 * @author xli
 * @since 2023-01-04
 */
@Slf4j
@Service
public class FrameModulePermissionServiceImpl extends ServiceImpl<FrameModulePermissionMapper, FrameModulePermission> implements IFrameModulePermissionService {

    @Override
    public boolean insert(FrameModulePermission frameModuleRight) {
        return this.save(frameModuleRight);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameModulePermission frameModuleRight) {
        return this.updateById(frameModuleRight);
    }

    @Override
    public FrameModulePermission find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<FrameModulePermission> findList(QueryWrapper<FrameModulePermission> qw) {
        return this.list(qw);
    }

    @Override
    public long findCount(QueryWrapper<FrameModulePermission> qw) {
        return this.count(qw);
    }

    @Override
    public Page<FrameModulePermission> findList(QueryWrapper<FrameModulePermission> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
