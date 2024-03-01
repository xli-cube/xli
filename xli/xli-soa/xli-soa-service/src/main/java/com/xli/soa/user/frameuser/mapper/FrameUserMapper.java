package com.xli.soa.user.frameuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.soa.user.frameuser.entity.FrameUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FrameUserMapper extends BaseMapper<FrameUser> {
}
