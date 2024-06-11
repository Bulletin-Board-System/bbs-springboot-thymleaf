package org.bbsgroup.bbs.util;

import org.bbsgroup.bbs.common.ResultCodeEnum;
import org.springframework.util.StringUtils;

import java.security.PublicKey;

public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String DEFAULT_FAIL_MESSAGE = "failed";
    private static final ResultCodeEnum DEFAULT_SUCCESS_CODE = ResultCodeEnum.SUCCESS;
    private static final ResultCodeEnum DEFAULT_FAIL_CODE = ResultCodeEnum.BAD_REQUEST;

    // 默认成功响应体（code 200）
    public static Result genSuccessResult(Object data) {
        return new Result(ResultCodeEnum.SUCCESS, DEFAULT_SUCCESS_MESSAGE, data);
    }

    // 自定义 message
    public static Result genSuccessResult(String message, Object data) {
        return new Result(ResultCodeEnum.SUCCESS, message, data);
    }

    // 默认失败响应体（code 400）
    public static Result genFailResult() {
        return new Result(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE, null);
    }

    // 自定义 message
    public static Result genFailResult(String message) {
        return new Result(DEFAULT_FAIL_CODE, message, null);
    }

    // BAD_REQUEST(400) 响应体
    public static Result genBadRequestResult(String message) {
        return new Result(ResultCodeEnum.BAD_REQUEST, message, null);
    }

    //  NOT_FOUND(404) 响应体
    public static Result genNotFoundResult(String message) {
        return new Result(ResultCodeEnum.NOT_FOUND, message, null);
    }

    //  UNAUTHORIZED(401) 响应体
    public static Result genUnAuthorizedResult(String message) {
        return new Result(ResultCodeEnum.UNAUTHORIZED, message, null);
    }

    //  CONFLICT(409) 响应体
    public static Result genConflictResult(String message) {
        return new Result(ResultCodeEnum.CONFLICT, message, null);
    }

    //  INTERNAL_SERVER_ERROR(500) 响应体
    public static Result genInternalServerError(String message) {
        return new Result(ResultCodeEnum.INTERNAL_SERVER_ERROR, message, null);
    }
}
