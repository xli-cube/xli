package com.xli.mis.tablestruct.entity.mapper;

import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.entity.dto.TableStructAddDTO;
import com.xli.mis.tablestruct.entity.dto.TableStructDTO;
import com.xli.mis.tablestruct.entity.dto.TableStructUpdateDTO;
import com.xli.mis.tablestruct.entity.vo.TableStructVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ITableStructMapper {

    ITableStructMapper INSTANCE = Mappers.getMapper(ITableStructMapper.class);

    @Mappings({
            @Mapping(source = "tableId", target = "table_id"),
            @Mapping(source = "fieldNameEn", target = "field_name_en"),
            @Mapping(source = "fieldNameCn", target = "field_name_cn"),
            @Mapping(source = "fieldType", target = "field_type"),
            @Mapping(source = "fieldLength", target = "field_length"),
            @Mapping(source = "controlType", target = "control_type"),
            @Mapping(source = "codeId", target = "code_id"),
            @Mapping(source = "showInTable", target = "show_in_table"),
            @Mapping(source = "showInDetail", target = "show_in_detail"),
            @Mapping(source = "showInSearch", target = "show_in_search"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    TableStruct toEntity(TableStructAddDTO tableStructDTO);

    @Mappings({
            @Mapping(source = "tableId", target = "table_id"),
            @Mapping(source = "fieldNameEn", target = "field_name_en"),
            @Mapping(source = "fieldNameCn", target = "field_name_cn"),
            @Mapping(source = "fieldType", target = "field_type"),
            @Mapping(source = "fieldLength", target = "field_length"),
            @Mapping(source = "controlType", target = "control_type"),
            @Mapping(source = "codeId", target = "code_id"),
            @Mapping(source = "showInTable", target = "show_in_table"),
            @Mapping(source = "showInDetail", target = "show_in_detail"),
            @Mapping(source = "showInSearch", target = "show_in_search"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    TableStruct toEntity(TableStructUpdateDTO tableStructDTO);

    @Mappings({
            @Mapping(source = "table_id", target = "tableId"),
            @Mapping(source = "field_name_en", target = "fieldNameEn"),
            @Mapping(source = "field_name_cn", target = "fieldNameCn"),
            @Mapping(source = "field_type", target = "fieldType"),
            @Mapping(source = "field_length", target = "fieldLength"),
            @Mapping(source = "control_type", target = "controlType"),
            @Mapping(source = "code_id", target = "codeId"),
            @Mapping(source = "show_in_table", target = "showInTable"),
            @Mapping(source = "show_in_detail", target = "showInDetail"),
            @Mapping(source = "show_in_search", target = "showInSearch"),
            @Mapping(source = "order_num", target = "orderNum")
    })
    TableStructVO toVO(TableStruct tableStruct);
}
