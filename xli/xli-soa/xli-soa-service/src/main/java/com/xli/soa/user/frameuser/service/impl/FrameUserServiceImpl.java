package com.xli.soa.user.frameuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.soa.user.frameuser.entity.FrameUser;
import com.xli.soa.user.frameuser.mapper.FrameUserMapper;
import com.xli.soa.user.frameuser.service.IFrameUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FrameUserServiceImpl extends ServiceImpl<FrameUserMapper, FrameUser> implements IFrameUserService {


    @Override
    public boolean insert(FrameUser frameUser) {
        return this.save(frameUser);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(FrameUser frameUser) {
        return this.updateById(frameUser);
    }

    @Override
    public FrameUser find(Long id) {
        return this.getById(id);
    }

    @Override
    public long findCount(QueryWrapper<FrameUser> qw) {
        return this.count(qw);
    }

    @Override
    public List<FrameUser> findList(QueryWrapper<FrameUser> qw) {
        return this.list(qw);
    }

    @Override
    public Page<FrameUser> findList(QueryWrapper<FrameUser> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
    @Override
    public FrameUser getFrameUserByLoginId(String loginId) {
        return null;
    }
}
