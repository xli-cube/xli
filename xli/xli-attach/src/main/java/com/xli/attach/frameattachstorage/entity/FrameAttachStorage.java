package com.xli.attach.frameattachstorage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.InputStream;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("frame_attachstorage")
public class FrameAttachStorage implements Serializable {

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
     * 文件名称
     */
    @TableField("file_name")
    private String file_name;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long file_size;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String file_type;

    /**
     * 文件分组
     */
    @TableField("group_id")
    private Long group_id;

    /**
     * 文件流
     */
    @TableField("file_content")
    private InputStream file_content;
}
