package com.xli.metadata.codeitems.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.metadata.codeitems.entity.CodeItems;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CodeItemsMapper extends BaseMapper<CodeItems> {

}
