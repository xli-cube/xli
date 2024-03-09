package com.xli.soa.module.framemodule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("frame_module")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FrameModule implements Serializable {

    @Serial
    private static final long serialVersionUID = 3603310951603307727L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long row_id;

    private String module_name;

    private String type;

    private String component;

    private Long pid;

    private String icon;

    private String is_disable;

    private int order_num;
}
