package com.junling.common.exception;

public enum BizCodeEnume {

    UNKNOWN_EXCEPTION(10000, "System unknown exception"),
    VALID_EXCEPTION(10001, "validation failed");


    private int code;
    private String msg;
    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
