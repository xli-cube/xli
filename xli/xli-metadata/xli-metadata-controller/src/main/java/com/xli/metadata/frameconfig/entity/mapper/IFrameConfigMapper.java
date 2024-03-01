package com.xli.metadata.frameconfig.entity.mapper;

import com.xli.metadata.frameconfig.entity.FrameConfig;
import com.xli.metadata.frameconfig.entity.dto.FrameConfigDTO;
import com.xli.metadata.frameconfig.entity.vo.FrameConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameConfigMapper {

    IFrameConfigMapper INSTANCE = Mappers.getMapper(IFrameConfigMapper.class);

    @Mappings({
            @Mapping(source = "configName", target = "config_name"),
            @Mapping(source = "configValue", target = "config_value"),
            @Mapping(source = "categoryId", target = "category_id"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    FrameConfig toEntity(FrameConfigDTO frameConfigDTO);

    @Mappings({
            @Mapping(source = "config_name", target = "configName"),
            @Mapping(source = "config_value", target = "configValue"),
            @Mapping(source = "category_id", target = "categoryId"),
            @Mapping(source = "order_num", target = "orderNum")
    })
    FrameConfigVO toVO(FrameConfig frameConfig);
}
