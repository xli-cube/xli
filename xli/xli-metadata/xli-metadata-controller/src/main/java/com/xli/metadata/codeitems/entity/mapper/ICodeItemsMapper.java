package com.xli.metadata.codeitems.entity.mapper;

import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.entity.dto.CodeItemsDTO;
import com.xli.metadata.codeitems.entity.vo.CodeItemsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICodeItemsMapper {

    ICodeItemsMapper INSTANCE = Mappers.getMapper(ICodeItemsMapper.class);

    @Mappings({
            @Mapping(source = "itemValue", target = "item_value"),
            @Mapping(source = "itemText", target = "item_text"),
            @Mapping(source = "codeId", target = "code_id"),
            @Mapping(source = "orderNum", target = "order_num")})
    CodeItems toEntity(CodeItemsDTO dto);

    @Mappings({
            @Mapping(source = "item_value", target = "itemValue"),
            @Mapping(source = "item_text", target = "itemText"),
            @Mapping(source = "code_id", target = "codeId"),
            @Mapping(source = "order_num", target = "orderNum")})
    CodeItemsVO toVO(CodeItems codeItems);
}
