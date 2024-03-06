package com.xli.soa.role.framerole.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleAddDTO extends RoleDTO {

    @Serial
    private static final long serialVersionUID = -1020258975524581604L;

    @Schema(description = "角色名")
    @NotBlank(message = "roleName必填", groups = {IGroup.add.class})
    private String roleName;

}
