package com.xli.soa.role.framerolecategory.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RoleCategoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2556902869150986982L;

    @Schema(description = "分类名")
    @NotBlank(message = "categoryName必填", groups = {IGroup.add.class, IGroup.update.class})
    private String categoryName;

    @Schema(description = "排序")
    private Integer orderNum;
}
