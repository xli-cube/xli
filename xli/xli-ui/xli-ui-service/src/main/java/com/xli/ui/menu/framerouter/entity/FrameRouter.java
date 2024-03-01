package com.xli.ui.menu.framerouter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("frame_router")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FrameRouter implements Serializable {

    @Serial
    private static final long serialVersionUID = 9178671367562768261L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long row_id;

    private String menu_name;

    private String router_type;

    private String path;

    private String component;

    private Long pid;

    private String permission;

    private String icon;

    private String visible;

    private String enabled;

    private int order_num;
}
