package com.xli.metadata.frameconfigcategory.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.metadata.frameconfigcategory.entity.FrameConfigCategory;

import java.util.List;

public interface IFrameConfigCategoryService extends IService<FrameConfigCategory> {

    boolean insert(FrameConfigCategory frameConfigCategory);

    boolean delete(Long id);

    List<FrameConfigCategory> findList(QueryWrapper<FrameConfigCategory> qw);
}
