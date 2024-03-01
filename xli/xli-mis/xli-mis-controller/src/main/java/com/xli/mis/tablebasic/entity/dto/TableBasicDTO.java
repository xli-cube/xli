package com.xli.mis.tablebasic.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TableBasicDTO {

    @NotNull(message = "id不能为空", groups = {IGroup.update.class, IGroup.delete.class, IGroup.detail.class})
    private String id;

    @NotBlank(message = "tableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String tableName;

    @NotBlank(message = "sqlTableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String sqlTableName;

    private String entityName;

    private String datasourceId;

    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
