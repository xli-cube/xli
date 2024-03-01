package com.xli.soa.ou.frameou.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class OuAddDTO extends OuDTO{

	@Serial
	private static final long serialVersionUID = 3648410787734719061L;
}
