package com.xli.soa.role.framerolecategory.entity.mapper;

import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;
import com.xli.soa.role.framerolecategory.entity.dto.FrameRoleCategoryDTO;
import com.xli.soa.role.framerolecategory.entity.vo.FrameRoleCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameRoleCategoryMapper {

    IFrameRoleCategoryMapper INSTANCE = Mappers.getMapper(IFrameRoleCategoryMapper.class);

    FrameRoleCategory toEntity(FrameRoleCategoryDTO dto);

    FrameRoleCategoryVO toVO(FrameRoleCategory frameRoleCategory);

}
