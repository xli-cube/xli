package com.xli.ui.menu.framerouter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.ui.menu.framerouter.entity.FrameRouter;
import com.xli.ui.menu.framerouter.mapper.FrameRouterMapper;
import com.xli.ui.menu.framerouter.service.IFrameRouterService;
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
public class FrameRouterServiceImpl extends ServiceImpl<FrameRouterMapper, FrameRouter> implements IFrameRouterService {

    @Override
    public boolean insert(FrameRouter frameRouter) {
        return this.save(frameRouter);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameRouter frameRouter) {
        return this.updateById(frameRouter);
    }

    @Override
    public FrameRouter find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<FrameRouter> findList(QueryWrapper<FrameRouter> qw) {
        return this.list(qw);
    }

    @Override
    public long findCount(QueryWrapper<FrameRouter> qw) {
        return this.count(qw);
    }

    @Override
    public Page<FrameRouter> findList(QueryWrapper<FrameRouter> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public List<FrameRouter> getFrameRouterListByUserId(String userId) {
        return null;
    }
}
