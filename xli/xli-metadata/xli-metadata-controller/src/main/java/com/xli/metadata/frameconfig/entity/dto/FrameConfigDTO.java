package com.xli.metadata.frameconfig.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "系统参数请求体")
public class FrameConfigDTO {

    /**
     * 主键
     */
    @Schema(description = "系统参数标识")
    @NotNull(message = "id不能为空", groups = {IGroup.delete.class, IGroup.update.class, IGroup.detail.class})
    private Long id;

    /**
     * 参数名
     */
    @Schema(description = "参数名")
    @NotBlank(message = "configName不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String configName;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    @NotBlank(message = "configValue不能为空", groups = {IGroup.add.class, IGroup.update.class})
    private String configValue;

    /**
     * 参数描述
     */
    @Schema(description = "参数描述")
    private String description;

    /**
     * 分类id
     */
    @Schema(description = "参数分类")
    private String categoryId;

    /**
     * 排序
     */
    @Schema(description = "排序号")
    private Integer orderNum;

    @NotNull(message = "current必填", groups = {IGroup.search.class})
    private Integer current;

    @NotNull(message = "pageSize必填", groups = {IGroup.search.class})
    private Integer pageSize;

    private String sort;

    private String filter;
}
