package com.xli.attach.frameattachinfo.entity.mapper;

import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachinfo.entity.dto.FrameAttachInfoDTO;
import com.xli.attach.frameattachinfo.entity.vo.FrameAttachInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IFrameAttachInfoMapper {

    IFrameAttachInfoMapper INSTANCE = Mappers.getMapper(IFrameAttachInfoMapper.class);

    @Mappings({
            @Mapping(source = "fileName", target = "file_name"),
            @Mapping(source = "fileType", target = "file_type"),
            @Mapping(source = "groupId", target = "group_id"),
            @Mapping(source = "fileSize", target = "file_size"),
            @Mapping(source = "hashCode", target = "hash_code"),
            @Mapping(source = "orderNum", target = "order_num")
    })
    FrameAttachInfo toEntity(FrameAttachInfoDTO dto);

    @Mappings({
            @Mapping(source = "file_name", target = "fileName"),
            @Mapping(source = "file_type", target = "fileType"),
            @Mapping(source = "group_id", target = "groupId"),
            @Mapping(source = "file_size", target = "fileSize"),
            @Mapping(source = "hash_code", target = "hashCode"),
            @Mapping(source = "order_num", target = "orderNum")
    })
    FrameAttachInfoVO toVO(FrameAttachInfo frameAttachinfo);
}
