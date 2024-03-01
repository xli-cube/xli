package com.xli.soa.role.framerole.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FrameRoleVO implements Serializable {

	@Serial
	private static final long serialVersionUID = -3594002208761100075L;

	private String id;

	private String roleName;

	private String categoryId;

	private Integer orderNum;

}
