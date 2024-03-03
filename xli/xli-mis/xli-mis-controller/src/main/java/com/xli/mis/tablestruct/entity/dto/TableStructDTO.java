package com.xli.mis.tablestruct.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class TableStructDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3385461775058599742L;

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
}
