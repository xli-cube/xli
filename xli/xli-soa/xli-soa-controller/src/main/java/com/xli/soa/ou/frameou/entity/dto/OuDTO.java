package com.xli.soa.ou.frameou.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class OuDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 6507548256083527690L;

	private String ouName;

	private String ouCode;

	private String ouShortname;

	private String pid;

	private String tel;

	private String description;

	private Integer orderNum;
}
