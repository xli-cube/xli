package com.xli.soa.user.frameuser.entity.dto;

import com.xli.dto.search.SearchDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchDTO extends SearchDTO {

    @Serial
    private static final long serialVersionUID = -6310286858943395659L;

    @Schema(description = "账号")
    private String loginId;

    @Schema(description = "用户名")
    private String displayName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "所属部门")
    private String ouId;

    @Schema(description = "启用状态")
    private String isEnabled;
}
