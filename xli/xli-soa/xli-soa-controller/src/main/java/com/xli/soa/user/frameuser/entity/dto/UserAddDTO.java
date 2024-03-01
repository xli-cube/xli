package com.xli.soa.user.frameuser.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddDTO extends UserDTO {

    @Serial
    private static final long serialVersionUID = 4211956559577099675L;

    @Schema(description = "账号")
    @NotBlank(message = "loginId必填", groups = {IGroup.add.class})
    private String loginId;
}
