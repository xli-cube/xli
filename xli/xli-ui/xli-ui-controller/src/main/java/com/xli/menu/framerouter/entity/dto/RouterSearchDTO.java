package com.xli.menu.framerouter.entity.dto;

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
public class RouterSearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = 797488586151433888L;

    private String menuName;

    private String pid;
}
