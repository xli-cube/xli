package com.xli.soa.role.framerolecategory.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FrameRoleCategoryVO implements Serializable {

	@Serial
	private static final long serialVersionUID = -7446927322471157132L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 角色分类
	 */
	private String CategoryName;

	/**
	 * 排序
	 */
	private Integer orderNum;

}
