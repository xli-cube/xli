package com.xli.mis.tablebasic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.mis.tablebasic.entity.TableBasic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TableBasicMapper extends BaseMapper<TableBasic> {

    boolean tableExists(@Param("sqlTableName") String sqlTableName);

    void createTable(@Param("sqlTableName") String sqlTableName, @Param("tableName") String tableName);

    void dropTable(@Param("sqlTableName") String sqlTableName);
}
