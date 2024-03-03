package com.xli.mis.tablebasic.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class TableBasicUpdateDTO extends TableBasicDTO{

    @Serial
    private static final long serialVersionUID = -5488998690016527638L;

    @Schema(description = "主键id")
    @NotBlank(message = "id必填", groups = {IGroup.update.class})
    private String id;
}
