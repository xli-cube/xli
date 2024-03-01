package com.xli.soa.ou.frameou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("frame_ou")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class FrameOu implements Serializable {

    @Serial
    private static final long serialVersionUID = 5763981553316337920L;

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
     * 组织机构编码
     */
    private String ou_code;

    /**
     * 组织机构名称
     */
    private String ou_name;

    /**
     * 父部门
     */
    private Long pid;

    /**
     * 组织机构简称
     */
    private String ou_shortname;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 简介
     */
    private String description;

    /**
     * 排序
     */
    private Integer order_num;
}
