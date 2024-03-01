package com.xli.soa.role.framerole.entity.mapper;

import com.xli.soa.role.framerole.entity.FrameRole;
import com.xli.soa.role.framerole.entity.dto.FrameRoleDTO;
import com.xli.soa.role.framerole.entity.vo.FrameRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameRoleMapper {

    IFrameRoleMapper INSTANCE = Mappers.getMapper(IFrameRoleMapper.class);

    @Mappings({
            @Mapping(source = "roleName", target = "role_name"),
            @Mapping(source = "categoryId", target = "category_id"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameRole toEntity(FrameRoleDTO dto);

    @Mappings({
            @Mapping(source = "role_name", target = "roleName"),
            @Mapping(source = "category_id", target = "categoryId"),
            @Mapping(source = "order_num", target = "orderNum")})
    FrameRoleVO toVO(FrameRole frameRole);

}
