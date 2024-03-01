package com.xli.metadata.frameconfig.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.metadata.frameconfig.entity.FrameConfig;

import java.util.List;

public interface IFrameConfigService extends IService<FrameConfig> {

    boolean insert(FrameConfig frameConfig);

    boolean delete(Long id);

    boolean update(FrameConfig frameConfig);

    FrameConfig find(Long id);

    List<FrameConfig> findList(QueryWrapper<FrameConfig> qw);

    Page<FrameConfig> findList(QueryWrapper<FrameConfig> qw, long current, long size);

    String getConfigValue(String configName);
}
