package com.xli.soa.role.framerolecategory.entity.mapper;

import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategoryAddDTO;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategoryUpdateDTO;
import com.xli.soa.role.framerolecategory.entity.vo.RoleCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameRoleCategoryMapper {

    IFrameRoleCategoryMapper INSTANCE = Mappers.getMapper(IFrameRoleCategoryMapper.class);

    @Mappings({
            @Mapping(source = "categoryName", target = "category_name"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameRoleCategory toEntity(RoleCategoryAddDTO dto);

    @Mappings({
            @Mapping(source = "categoryName", target = "category_name"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameRoleCategory toEntity(RoleCategoryUpdateDTO dto);

    @Mappings({
            @Mapping(source = "category_name", target = "categoryName"),
            @Mapping(source = "order_num", target = "orderNum")})
    RoleCategoryVO toVO(FrameRoleCategory frameRoleCategory);
}
