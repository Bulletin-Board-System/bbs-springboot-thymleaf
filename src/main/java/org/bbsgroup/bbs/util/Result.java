package org.bbsgroup.bbs.util;

import org.bbsgroup.bbs.common.ResultCodeEnum;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public Result(ResultCodeEnum resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
    }

    public Result(ResultCodeEnum resultCode, String message, T data) {
        this.code = resultCode.getCode();
        this.message = message;
        this.data = data;
    }

    public int getResultCodeValue() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
