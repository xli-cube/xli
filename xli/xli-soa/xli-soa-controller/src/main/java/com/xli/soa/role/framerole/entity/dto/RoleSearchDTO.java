package com.xli.soa.role.framerole.entity.dto;

import com.xli.dto.search.SearchDTO;
import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleSearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = -708631155177229314L;

    @Schema(description = "角色名")
    private String roleName;

    @Schema(description = "角色分类")
    private String categoryId;
}
