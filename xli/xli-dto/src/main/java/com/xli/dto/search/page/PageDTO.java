package com.xli.dto.search.page;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class PageDTO implements Serializable {

    @Schema(description = "当前页")
    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @Schema(description = "每页数量")
    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    public Integer getCurrent() {
        return Optional.of(current).orElse(1);
    }

    public Integer getPageSize() {
        return Optional.of(pageSize).orElse(20);
    }
}
