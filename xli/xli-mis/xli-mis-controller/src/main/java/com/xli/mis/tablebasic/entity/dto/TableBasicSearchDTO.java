package com.xli.mis.tablebasic.entity.dto;

import com.xli.dto.search.SearchDTO;
import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class TableBasicSearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = 2938399276152228940L;

    @NotBlank(message = "tableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String tableName;

    @NotBlank(message = "sqlTableName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String sqlTableName;
}
