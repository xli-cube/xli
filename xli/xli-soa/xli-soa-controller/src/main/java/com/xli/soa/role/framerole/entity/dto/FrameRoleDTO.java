package com.xli.soa.role.framerole.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FrameRoleDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色分类
     */
    private String categoryId;

    /**
     * 排序
     */
    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
