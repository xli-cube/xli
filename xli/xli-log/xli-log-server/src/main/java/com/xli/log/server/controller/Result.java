package com.xli.log.server.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Result {
    public static Result UN_LOGIN = new Result(401);
    public static Result INVALID_LOGIN = new Result(402);
    private Integer code;
    private String message;
    private List<String> logs = new ArrayList<>();
    private Object data;

    public Result() {
    }

    public Result(Integer code) {
        this.code = code;
    }

}
