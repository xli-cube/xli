package com.xli.metadata.frameconfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.metadata.frameconfig.entity.FrameConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FrameConfigMapper extends BaseMapper<FrameConfig> {

}
