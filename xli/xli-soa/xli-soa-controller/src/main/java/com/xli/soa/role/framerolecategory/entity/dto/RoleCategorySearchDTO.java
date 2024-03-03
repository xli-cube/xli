package com.xli.soa.role.framerolecategory.entity.dto;

import com.xli.dto.search.SearchDTO;
import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCategorySearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = 3885440688275724896L;

    @Schema(description = "分类名")
    private String categoryName;
}
