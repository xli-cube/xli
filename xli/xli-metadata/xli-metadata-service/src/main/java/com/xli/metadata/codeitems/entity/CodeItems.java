package com.xli.metadata.codeitems.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("code_items")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeItems implements Serializable {

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
     * 字典值
     */
    private String item_value;

    /**
     * 字典文本
     */
    private String item_text;

    /**
     * 父项
     */
    private Long pid;

    /**
     * 字典id
     */
    private Long code_id;

    /**
     * 排序
     */
    private Integer order_num;
}
