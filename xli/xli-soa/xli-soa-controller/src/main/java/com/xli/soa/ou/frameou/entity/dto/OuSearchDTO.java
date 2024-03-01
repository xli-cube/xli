package com.xli.soa.ou.frameou.entity.dto;

import com.xli.dto.search.SearchDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class OuSearchDTO extends SearchDTO {

	@Serial
	private static final long serialVersionUID = 8386472094192283154L;

	private String ouName;

	private String ouCode;

	private String pid;
}
