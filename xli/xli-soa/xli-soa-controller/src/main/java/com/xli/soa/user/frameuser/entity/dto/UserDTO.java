package com.xli.soa.user.frameuser.entity.dto;

import com.xli.dto.validation.group.IGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8504674618327963824L;

    @Schema(description = "用户名")
    @NotBlank(message = "displayName必填", groups = {IGroup.add.class, IGroup.update.class})
    private String displayName;

    @Schema(description = "性别")
    @NotBlank(message = "gender必填", groups = {IGroup.add.class, IGroup.update.class})
    private String gender;

    @Schema(description = "身份证号")
    @NotBlank(message = "idNum必填", groups = {IGroup.add.class, IGroup.update.class})
    private String idNum;

    @Schema(description = "手机号")
    @NotBlank(message = "mobile必填", groups = {IGroup.add.class, IGroup.update.class})
    private String mobile;

    @Schema(description = "所属部门")
    @NotBlank(message = "ouId必填", groups = {IGroup.add.class, IGroup.update.class})
    private String ouId;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "个人头像")
    private String picture;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "车牌号")
    private String carNum;

    @Schema(description = "个人描述")
    private String description;

    @Schema(description = "职位")
    private String post;

    @Schema(description = "工作描述")
    private String workSituation;

    @Schema(description = "启用状态")
    private String isEnabled;

    @Schema(description = "排序")
    private int orderNum;
}
