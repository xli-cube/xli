package com.xli.soa.ou.frameou.entity.mapper;

import com.xli.soa.ou.frameou.entity.FrameOu;
import com.xli.soa.ou.frameou.entity.dto.OuAddDTO;
import com.xli.soa.ou.frameou.entity.dto.OuUpdateDTO;
import com.xli.soa.ou.frameou.entity.vo.OuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameOuMapper {

    IFrameOuMapper INSTANCE = Mappers.getMapper(IFrameOuMapper.class);

    @Mappings({
            @Mapping(source = "ouCode", target = "ou_code"),
            @Mapping(source = "ouName", target = "ou_name"),
            @Mapping(source = "ouShortname", target = "ou_shortname"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameOu toEntity(OuAddDTO dto);

    @Mappings({
            @Mapping(source = "ouCode", target = "ou_code"),
            @Mapping(source = "ouName", target = "ou_name"),
            @Mapping(source = "ouShortname", target = "ou_shortname"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameOu toEntity(OuUpdateDTO dto);

    @Mappings({
            @Mapping(source = "ou_code", target = "ouCode"),
            @Mapping(source = "ou_name", target = "ouName"),
            @Mapping(source = "ou_shortname", target = "ouShortname"),
            @Mapping(source = "order_num", target = "orderNum")})
    OuVO toVO(FrameOu frameOu);

}
