package com.xli.mis.tablestruct.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TableStructDTO {

    @NotNull(message = "id不能为空", groups = {IGroup.update.class, IGroup.delete.class, IGroup.detail.class})
    private String id;

    @NotBlank(message = "fieldNameEn不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String fieldNameEn;

    @NotBlank(message = "fieldNameCn不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String fieldNameCn;

    @NotBlank(message = "fieldType不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String fieldType;

    private String controlType;

    private Integer fieldLength;

    private String notnull;

    @NotNull(message = "tableId不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String tableId;

    private String codeId;

    private String showInTable;

    private String showInDetail;

    private String showInSearch;

    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
