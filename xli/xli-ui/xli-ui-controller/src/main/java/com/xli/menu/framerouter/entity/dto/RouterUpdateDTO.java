package com.xli.menu.framerouter.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class RouterUpdateDTO extends RouterDTO {

    @Serial
    private static final long serialVersionUID = -2081014473585139867L;

    @NotBlank(message = "id必传", groups = {IGroup.update.class, IGroup.detail.class, IGroup.delete.class})
    private String id;
}
