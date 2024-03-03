package com.xli.soa.role.framerolecategory.entity.dto;

import com.xli.dto.validation.group.IGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCategoryAddDTO extends RoleCategoryDTO{

    @Serial
    private static final long serialVersionUID = 2366473678206150668L;
}
