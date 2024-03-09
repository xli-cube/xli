package com.xli.soa.module.framemodule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.soa.module.framemodule.entity.FrameModule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FrameModuleMapper extends BaseMapper<FrameModule> {
}
