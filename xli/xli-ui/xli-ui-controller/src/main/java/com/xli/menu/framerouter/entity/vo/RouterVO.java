package com.xli.menu.framerouter.entity.vo;

import lombok.Data;

@Data
public class RouterVO {

    private String id;

    private String menuName;

    private String routerType;

    private String path;

    private String component;

    private String pid;

    private String icon;

    private String visible;

    private String enabled;

    private String orderNum;
}
