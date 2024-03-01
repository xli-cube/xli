package com.xli.soa.role.framerole.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("frame_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrameRole implements Serializable {

	@Serial
	private static final long serialVersionUID = 2869814344050472826L;

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
	 * 角色名称
	 */
	private String role_name;

	/**
	 * 角色分类
	 */
	private String category_id;

	/**
	 * 排序
	 */
	private Integer order_num;

}
