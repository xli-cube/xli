package com.xli.metadata.codemain.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CodeMainDTO {

    private String id;

    private String codeName;

    private String categoryId;

    private String description;

    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
