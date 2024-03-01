package com.xli.attach.frameattachconfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("frame_attachconfig")
public class FrameAttachConfig implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 自增键
     */
    @TableField("row_id")
    private Long row_id;

    /**
     * 存储方式
     */
    @TableField("storage_name")
    private String storage_name;

    /**
     * 连接字符串
     */
    @TableField("conn_string")
    private String conn_string;
}
