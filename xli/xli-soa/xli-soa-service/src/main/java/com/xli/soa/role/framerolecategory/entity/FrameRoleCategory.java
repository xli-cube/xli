package com.xli.soa.role.framerolecategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("frame_role_category")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrameRoleCategory implements Serializable {

	@Serial
	private static final long serialVersionUID = -969335152299811167L;

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
	 * 角色分类
	 */
	private String category_name;

	/**
	 * 排序
	 */
	private Integer order_num;

}
