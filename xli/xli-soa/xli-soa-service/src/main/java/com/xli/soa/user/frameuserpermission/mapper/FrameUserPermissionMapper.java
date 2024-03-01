package com.xli.soa.user.frameuserpermission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.soa.user.frameuserpermission.entity.FrameUserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FrameUserPermissionMapper extends BaseMapper<FrameUserPermission> {
}
