package com.xli.soa.user.frameuser.entity.mapper;

import com.xli.soa.user.frameuser.entity.FrameUser;
import com.xli.soa.user.frameuser.entity.dto.UserAddDTO;
import com.xli.soa.user.frameuser.entity.dto.UserUpdateDTO;
import com.xli.soa.user.frameuser.entity.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameUserMapper {

    IFrameUserMapper INSTANCE = Mappers.getMapper(IFrameUserMapper.class);

    @Mappings({
            @Mapping(source = "loginId", target = "login_id"),
            @Mapping(source = "ouId", target = "ou_id"),
            @Mapping(source = "displayName", target = "display_name"),
            @Mapping(source = "idNum", target = "id_num"),
            @Mapping(source = "carNum", target = "car_num"),
            @Mapping(source = "workSituation", target = "work_situation"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameUser toEntity(UserAddDTO dto);

    @Mappings({
            @Mapping(source = "ouId", target = "ou_id"),
            @Mapping(source = "displayName", target = "display_name"),
            @Mapping(source = "idNum", target = "id_num"),
            @Mapping(source = "carNum", target = "car_num"),
            @Mapping(source = "workSituation", target = "work_situation"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameUser toEntity(UserUpdateDTO dto);

    @Mappings({
            @Mapping(source = "login_id", target = "loginId"),
            @Mapping(source = "ou_id", target = "ouId"),
            @Mapping(source = "display_name", target = "displayName"),
            @Mapping(source = "id_num", target = "idNum"),
            @Mapping(source = "car_num", target = "carNum"),
            @Mapping(source = "work_situation", target = "workSituation"),
            @Mapping(source = "order_num", target = "orderNum")})
    UserVO toVO(FrameUser frameUser);

}
