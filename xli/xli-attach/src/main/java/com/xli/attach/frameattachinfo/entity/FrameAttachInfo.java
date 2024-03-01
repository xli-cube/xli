package com.xli.attach.frameattachinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;
import lombok.*;

import java.io.Serializable;

/**
 * 附件信息表
 *
 * @author xli
 * @since 2023-08-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("frame_attachinfo")
public class FrameAttachInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 附件名称
     */
    @TableField("file_name")
    private String file_name;

    /**
     * 附件类型
     */
    @TableField("file_type")
    private String file_type;

    /**
     * 分组
     */
    @TableField("group_id")
    private Long group_id;

    /**
     * 附件大小
     */
    @TableField("file_size")
    private Long file_size;

    /**
     * 存储方式
     */
    @TableField("storage_type")
    private String storage_type;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String file_path;

    /**
     * 附件哈希值
     */
    @TableField("hash_code")
    private String hash_code;

    /**
     * 附件提交人
     */
    @TableField("update_user_id")
    private Long update_user_id;

    /**
     * 附件提交人姓名
     */
    @TableField("update_user_name")
    private String update_user_name;

    /**
     * 排序值
     */
    @TableField("order_num")
    private Integer order_num;

    /**
     * 文件流
     */
    @TableField(exist = false)
    private FrameAttachStorage frameAttachStorage;
}
