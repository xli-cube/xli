package com.xli.soa.user.frameuser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.soa.user.frameuser.entity.FrameUser;

import java.util.List;

public interface IFrameUserService extends IService<FrameUser> {

    boolean insert(FrameUser frameUser);

    boolean delete(Long id);

    boolean update(FrameUser frameUser);

    FrameUser find(Long id);

    long findCount(QueryWrapper<FrameUser> qw);

    List<FrameUser> findList(QueryWrapper<FrameUser> qw);

    Page<FrameUser> findList(QueryWrapper<FrameUser> qw, long current, long size);

    FrameUser getFrameUserByLoginId(String loginId);
}
