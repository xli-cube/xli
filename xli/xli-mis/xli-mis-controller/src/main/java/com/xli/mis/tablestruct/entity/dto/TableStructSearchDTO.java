package com.xli.mis.tablestruct.entity.dto;

import com.xli.dto.search.SearchDTO;
import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class TableStructSearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = 847989148603094768L;

    @Schema(description = "字段英文名")
    private String fieldNameEn;

    @Schema(description = "字段中文名")
    private String fieldNameCn;

    @Schema(description = "所属物理表")
    private String tableId;
}
