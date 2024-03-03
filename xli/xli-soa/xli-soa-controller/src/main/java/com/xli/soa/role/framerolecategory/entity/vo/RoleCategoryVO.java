package com.xli.soa.role.framerolecategory.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RoleCategoryVO implements Serializable {

	@Serial
	private static final long serialVersionUID = -7446927322471157132L;

	private Long id;

	private String categoryName;

	private Integer orderNum;

}
