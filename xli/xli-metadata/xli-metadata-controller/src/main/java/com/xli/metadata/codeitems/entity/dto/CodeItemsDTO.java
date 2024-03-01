package com.xli.metadata.codeitems.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CodeItemsDTO {

    private String id;

    private String itemValue;

    private String itemText;

    private String pid;

    private String codeId;

    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
