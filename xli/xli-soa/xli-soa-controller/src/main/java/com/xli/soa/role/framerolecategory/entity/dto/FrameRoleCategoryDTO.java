package com.xli.soa.role.framerolecategory.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FrameRoleCategoryDTO {

    private Long id;

    private String categoryName;

    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
