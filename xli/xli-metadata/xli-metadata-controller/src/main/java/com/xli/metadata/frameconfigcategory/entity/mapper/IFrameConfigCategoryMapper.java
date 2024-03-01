package com.xli.metadata.frameconfigcategory.entity.mapper;

import com.xli.metadata.frameconfigcategory.entity.FrameConfigCategory;
import com.xli.metadata.frameconfigcategory.entity.dto.FrameConfigCategoryDTO;
import com.xli.metadata.frameconfigcategory.entity.vo.FrameConfigCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameConfigCategoryMapper {

    IFrameConfigCategoryMapper INSTANCE = Mappers.getMapper(IFrameConfigCategoryMapper.class);

    @Mappings({
            @Mapping(source = "categoryName", target = "category_name"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    FrameConfigCategory toEntity(FrameConfigCategoryDTO frameConfigCategoryDTO);

    @Mappings({
            @Mapping(source = "category_name", target = "categoryName"),
            @Mapping(source = "order_num", target = "orderNum")
    })
    FrameConfigCategoryVO toVO(FrameConfigCategory frameConfigCategory);
}
