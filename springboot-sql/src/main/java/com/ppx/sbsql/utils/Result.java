package com.ppx.sbsql.utils;

import lombok.Data;

@Data
public class Result<T> {
    private Boolean success;
    private String code = "";
    private String msg;
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result withSuccess(T model) {
        Result result = new Result(model);
        result.setSuccess(true);
        return result;
    }

    public static Result withError(String msg) {
        Result result = new Result(null);
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }
}
