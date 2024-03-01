package com.xli.soa.permission.framepermission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.soa.permission.framepermission.entity.FramePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FramePermissionMapper extends BaseMapper<FramePermission> {
}
