package com.xli.metadata.codemain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.metadata.codemain.entity.CodeMain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CodeMainMapper extends BaseMapper<CodeMain> {

}
