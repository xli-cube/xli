package com.xli.soa.user.frameuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("frame_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FrameUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -815915137598350706L;

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
     * 登录ID
     */
    private String login_id;

    /**
     * 用户名称
     */
    private String display_name;

    /**
     * 用户密码（加密后）
     */
    private String pwd;

    /**
     * 性别
     */
    private String gender;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 头像
     */
    private Long picture;

    /**
     * 所属部门
     */
    private String ou_id;

    /**
     * 排序
     */
    private int order_num;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用
     */
    private int is_enabled;

    /**
     * 职位
     */
    private String post;

    /**
     * 身份证号码
     */
    private String id_num;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 车牌
     */
    private int car_num;

    /**
     * 描述
     */
    private String description;

    /**
     * 工作情况
     */
    private int work_situation;
}
