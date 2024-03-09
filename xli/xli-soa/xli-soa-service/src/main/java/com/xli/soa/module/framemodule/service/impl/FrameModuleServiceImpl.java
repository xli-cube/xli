package com.xli.soa.module.framemodule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.module.framemodule.entity.FrameModule;
import com.xli.soa.module.framemodule.mapper.FrameModuleMapper;
import com.xli.soa.module.framemodule.service.IFrameModuleService;
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
public class FrameModuleServiceImpl extends ServiceImpl<FrameModuleMapper, FrameModule> implements IFrameModuleService {

    @Override
    public boolean insert(FrameModule frameModule) {
        return this.save(frameModule);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameModule frameModule) {
        return this.updateById(frameModule);
    }

    @Override
    public FrameModule find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<FrameModule> findList(QueryWrapper<FrameModule> qw) {
        return this.list(qw);
    }

    @Override
    public long findCount(QueryWrapper<FrameModule> qw) {
        return this.count(qw);
    }

    @Override
    public Page<FrameModule> findList(QueryWrapper<FrameModule> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
