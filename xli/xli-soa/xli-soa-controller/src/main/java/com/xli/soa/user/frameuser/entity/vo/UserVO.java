package com.xli.soa.user.frameuser.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -76095372629545406L;

    private String id;

    private String loginId;

    private String displayName;

    private String gender;

    private String mobile;

    private String ouId;

    private String ouName;

    private int orderNum;

    private int isEnabled;

    private String email;

    private String description;

    private String post;

    private String idNum;

    private String picture;

    private Date birthday;

    private String carNum;

    private String workSituation;
}
