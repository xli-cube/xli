package com.xli.metadata.codemain.entity.mapper;

import com.xli.metadata.codemain.entity.CodeMain;
import com.xli.metadata.codemain.entity.dto.CodeMainDTO;
import com.xli.metadata.codemain.entity.vo.CodeMainVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICodeMainMapper {

    ICodeMainMapper INSTANCE = Mappers.getMapper(ICodeMainMapper.class);

    @Mappings({
            @Mapping(source = "codeName", target = "code_name"),
            @Mapping(source = "categoryId", target = "category_id"),
            @Mapping(source = "orderNum", target = "order_num")})
    CodeMain toEntity(CodeMainDTO dto);

    @Mappings({
            @Mapping(source = "code_name", target = "codeName"),
            @Mapping(source = "category_id", target = "categoryId"),
            @Mapping(source = "order_num", target = "orderNum")})
    CodeMainVO toVO(CodeMain codeMain);
}
