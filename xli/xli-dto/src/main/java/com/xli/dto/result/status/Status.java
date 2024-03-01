package com.xli.dto.result.status;

import lombok.Getter;

@Getter
public enum Status {

    /**
     * 默认成功
     */
    SUCCESS("1", "操作成功"),

    /**
     * 默认失败
     */
    FAILED("0", "操作失败"),

    /**
     * 10X  权限相关异常代码
     */
    ERR_101("101", "认证失败，请重新登录"),

    /**
     * 20X  业务层相关异常代码
     */
    ERR_201("201", "参数校验失败，必填参数不能为空"),
    /**
     * 50X  数据库相关异常代码
     */
    UNKNOWN_ERROR("501", "系统繁忙,请稍后再试");

    private final String code;
    private final String msg;

    Status(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
