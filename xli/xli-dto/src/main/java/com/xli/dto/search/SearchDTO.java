package com.xli.dto.search;

import com.xli.dto.search.page.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SearchDTO extends PageDTO {

    @Schema(description = "排序字段")
    private String filter;

    @Schema(description = "排序方式")
    private String sort;
}
