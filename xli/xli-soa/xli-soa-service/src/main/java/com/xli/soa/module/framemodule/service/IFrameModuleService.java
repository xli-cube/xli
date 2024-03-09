package com.xli.soa.module.framemodule.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.module.framemodule.entity.FrameModule;

import java.util.List;

public interface IFrameModuleService extends IService<FrameModule> {

    boolean insert(FrameModule frameModule);

    boolean delete(Long id);

    boolean update(FrameModule frameModule);

    FrameModule find(Long id);

    List<FrameModule> findList(QueryWrapper<FrameModule> qw);

    long findCount(QueryWrapper<FrameModule> qw);

    Page<FrameModule> findList(QueryWrapper<FrameModule> qw, long current, long size);
}
