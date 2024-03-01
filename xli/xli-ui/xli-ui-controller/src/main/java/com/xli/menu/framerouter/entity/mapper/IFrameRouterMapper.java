package com.xli.menu.framerouter.entity.mapper;

import com.xli.menu.framerouter.entity.dto.RouterAddDTO;
import com.xli.menu.framerouter.entity.dto.RouterUpdateDTO;
import com.xli.menu.framerouter.entity.vo.RouterVO;
import com.xli.ui.menu.framerouter.entity.FrameRouter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface IFrameRouterMapper {

    IFrameRouterMapper INSTANCE = Mappers.getMapper(IFrameRouterMapper.class);
    @Mappings({
            @Mapping(source = "menuName", target = "menu_name"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameRouter toEntity(RouterAddDTO dto);

    @Mappings({
            @Mapping(source = "menuName", target = "menu_name"),
            @Mapping(source = "orderNum", target = "order_num")})
    FrameRouter toEntity(RouterUpdateDTO dto);

    @Mappings({
            @Mapping(source = "menu_name", target = "menuName"),
            @Mapping(source = "order_num", target = "orderNum")})
    RouterVO toVO(FrameRouter frameRouter);

}
