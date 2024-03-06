package com.xli.soa.role.framerole.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 838742592960831822L;

    @Schema(description = "角色分类")
    private String categoryId;

    @Schema(description = "排序")
    private Integer orderNum;
}
