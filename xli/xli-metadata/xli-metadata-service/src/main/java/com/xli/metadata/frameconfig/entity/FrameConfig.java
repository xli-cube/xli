package com.xli.metadata.frameconfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("frame_config")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrameConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 自增键
     */
    private Long row_id;

    /**
     * 参数名
     */
    private String config_name;

    /**
     * 参数值
     */
    private String config_value;

    /**
     * 分类id
     */
    private Long category_id;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer order_num;
}
