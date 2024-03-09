package com.xli.soa.module.framemodulepermission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("frame_module_permission")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FrameModulePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -7998299416304433744L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long row_id;

    private String module_id;

    private String allow_type;

    private String allow_to;
}
