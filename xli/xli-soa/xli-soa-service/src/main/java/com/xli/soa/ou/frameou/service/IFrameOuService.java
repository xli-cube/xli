package com.xli.soa.ou.frameou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.ou.frameou.entity.FrameOu;

import java.util.List;
import java.util.Set;

public interface IFrameOuService extends IService<FrameOu> {

    boolean insert(FrameOu frameOu);

    boolean delete(Long id);

    boolean update(FrameOu frameOu);

    FrameOu find(Long id);

    long findCount(QueryWrapper<FrameOu> qw);

    List<FrameOu> findList(QueryWrapper<FrameOu> qw);

    Page<FrameOu> findList(QueryWrapper<FrameOu> qw, long current, long size);
}
