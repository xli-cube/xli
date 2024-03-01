package com.xli.ui.menu.framerouter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.ui.menu.framerouter.entity.FrameRouter;

import java.util.List;

public interface IFrameRouterService extends IService<FrameRouter> {

    boolean insert(FrameRouter frameRouter);

    boolean delete(Long id);

    boolean update(FrameRouter frameRouter);

    FrameRouter find(Long id);

    List<FrameRouter> findList(QueryWrapper<FrameRouter> qw);

    long findCount(QueryWrapper<FrameRouter> qw);

    Page<FrameRouter> findList(QueryWrapper<FrameRouter> qw, long current, long size);

    List<FrameRouter> getFrameRouterListByUserId(String userId);
}
