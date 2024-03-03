package com.xli.mis.tablebasic.entity.mapper;

import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablebasic.entity.dto.TableBasicAddDTO;
import com.xli.mis.tablebasic.entity.dto.TableBasicDTO;
import com.xli.mis.tablebasic.entity.dto.TableBasicUpdateDTO;
import com.xli.mis.tablebasic.entity.vo.TableBasicVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ITableBasicMapper {

    ITableBasicMapper INSTANCE = Mappers.getMapper(ITableBasicMapper.class);

    @Mappings({
            @Mapping(source = "tableName", target = "table_name"),
            @Mapping(source = "sqlTableName", target = "sql_table_name"),
            @Mapping(source = "entityName", target = "entity_name"),
            @Mapping(source = "datasourceId", target = "datasource_id"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    TableBasic toEntity(TableBasicAddDTO tableBasicDTO);

    @Mappings({
            @Mapping(source = "tableName", target = "table_name"),
            @Mapping(source = "sqlTableName", target = "sql_table_name"),
            @Mapping(source = "entityName", target = "entity_name"),
            @Mapping(source = "datasourceId", target = "datasource_id"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    TableBasic toEntity(TableBasicUpdateDTO tableBasicDTO);

    @Mappings({
            @Mapping(source = "table_name", target = "tableName"),
            @Mapping(source = "sql_table_name", target = "sqlTableName"),
            @Mapping(source = "entity_name", target = "entityName"),
            @Mapping(source = "datasource_id", target = "datasourceId"),
            @Mapping(source = "order_num", target = "orderNum")
    })
    TableBasicVO toVO(TableBasic tableBasic);
}
