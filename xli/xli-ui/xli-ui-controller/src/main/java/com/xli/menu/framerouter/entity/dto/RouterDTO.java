package com.xli.menu.framerouter.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;


@Data
public class RouterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3397087444061224860L;

    @Schema(description = "菜单名称", example = "用户管理")
    @NotBlank(message = "menuName必填", groups = {IGroup.add.class, IGroup.update.class})
    private String menuName;

    @Schema(description = "路由类型")
    @NotBlank(message = "routerType必填", groups = {IGroup.add.class, IGroup.update.class})
    private String routerType;

    @Schema(description = "路径")
    @NotBlank(message = "path必填", groups = {IGroup.add.class, IGroup.update.class})
    private String path;

    @Schema(description = "组件")
    private String component;

    @Schema(description = "上级菜单")
    private String pid;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "是否隐藏")
    @NotBlank(message = "visible必填", groups = {IGroup.add.class, IGroup.update.class})
    private String visible;

    @Schema(description = "是否启用")
    @NotBlank(message = "enabled必填", groups = {IGroup.add.class, IGroup.update.class})
    private String enabled;

    @Schema(description = "排序")
    private String orderNum;
}
