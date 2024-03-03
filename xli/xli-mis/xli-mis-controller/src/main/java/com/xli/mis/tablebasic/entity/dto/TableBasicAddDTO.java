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
public class TableBasicAddDTO extends TableBasicDTO{

    @Serial
    private static final long serialVersionUID = -8267441561466515923L;
}
