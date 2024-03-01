package com.xli.soa.ou.frameou.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class OuVO implements Serializable {

	@Serial
	private static final long serialVersionUID = -7481455347075258153L;

	private String id;

	private String ouCode;

	private String ouName;

	private String ouShortname;

	private String description;

	private String tel;

	private String pid;

	private Integer orderNum;
}
