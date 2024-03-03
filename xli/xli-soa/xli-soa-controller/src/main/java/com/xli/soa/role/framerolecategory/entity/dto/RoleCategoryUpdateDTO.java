package com.xli.soa.role.framerolecategory.entity.dto;

import com.xli.dto.validation.group.IGroup;
import com.xli.soa.user.frameuser.entity.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCategoryUpdateDTO extends RoleCategoryDTO {

    @Serial
    private static final long serialVersionUID = -8164170824970021097L;

    @Schema(description = "主键id")
    @NotBlank(message = "id必填", groups = {IGroup.update.class})
    private Long id;
}
