package com.xli.mis.tablebasic.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class TableBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2336550959542546522L;

    @NotBlank(message = "tableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String tableName;

    @NotBlank(message = "sqlTableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String sqlTableName;

    private String entityName;

    private String datasourceId;

    private Integer orderNum;
}
