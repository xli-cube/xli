package com.xli.metadata.codemain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("code_main")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeMain implements Serializable {

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
     * 字典名
     */
    private String code_name;

    /**
     * 分类id
     */
    private Long category_id;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer order_num;
}
