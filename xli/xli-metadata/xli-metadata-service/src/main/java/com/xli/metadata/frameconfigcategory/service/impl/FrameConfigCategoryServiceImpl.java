package com.xli.metadata.frameconfigcategory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.metadata.frameconfigcategory.entity.FrameConfigCategory;
import com.xli.metadata.frameconfigcategory.mapper.FrameConfigCategoryMapper;
import com.xli.metadata.frameconfigcategory.service.IFrameConfigCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameConfigCategoryServiceImpl extends ServiceImpl<FrameConfigCategoryMapper, FrameConfigCategory>
        implements IFrameConfigCategoryService {

    @Override
    public boolean insert(FrameConfigCategory frameConfigCategory) {
        return this.save(frameConfigCategory);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<FrameConfigCategory> findList(QueryWrapper<FrameConfigCategory> qw) {
        return this.list(qw);
    }
}
