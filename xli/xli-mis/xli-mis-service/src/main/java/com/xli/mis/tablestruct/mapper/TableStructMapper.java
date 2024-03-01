package com.xli.mis.tablestruct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xli.mis.tablestruct.entity.TableStruct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TableStructMapper extends BaseMapper<TableStruct> {

    boolean columnExists(@Param("sqlTableName") String sqlTableName, @Param("fieldNameEn") String fieldNameEn);

    void createField(@Param("sqlTableName") String sqlTableName, @Param("fieldNameEn") String fieldNameEn, @Param("fieldNameCn") String fieldNameCn, @Param("fieldTypeFull") String fieldTypeFull, @Param("notnull") String notnull);

    void dropField(@Param("sqlTableName") String sqlTableName, @Param("fieldNameEn") String fieldNameEn);
}
