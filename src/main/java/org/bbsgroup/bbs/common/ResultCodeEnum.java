package org.bbsgroup.bbs.common;

public enum ResultCodeEnum {
    SUCCESS(200),
    NOT_FOUND(404),
    UNAUTHORIZED(401),
    BAD_REQUEST(400),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ResultCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
