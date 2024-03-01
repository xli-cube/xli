package com.xli.metadata.frameconfig.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.metadata.frameconfig.entity.FrameConfig;
import com.xli.metadata.frameconfig.mapper.FrameConfigMapper;
import com.xli.metadata.frameconfig.service.IFrameConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameConfigServiceImpl extends ServiceImpl<FrameConfigMapper, FrameConfig> implements IFrameConfigService {

    private static final String CACHE_NAME = "xli.frameConfig.cache";

    @Override
    public boolean insert(FrameConfig frameConfig) {
        return this.save(frameConfig);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameConfig frameConfig) {
        return this.updateById(frameConfig);
    }

    @Override
    public FrameConfig find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<FrameConfig> findList(QueryWrapper<FrameConfig> qw) {
        return this.list(qw);
    }

    @Override
    public Page<FrameConfig> findList(QueryWrapper<FrameConfig> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public String getConfigValue(String configName) {
        if (StrUtil.isNotBlank(configName)) {
            QueryWrapper<FrameConfig> qw = new QueryWrapper<>();
            qw.lambda().eq(FrameConfig::getConfig_name, configName);
            List<FrameConfig> frameConfigList = findList(qw);
            if (!frameConfigList.isEmpty()) {
                return frameConfigList.get(0).getConfig_value();
            }
        }
        return "";
    }
}
