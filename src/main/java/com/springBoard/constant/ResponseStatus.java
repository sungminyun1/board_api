package com.springBoard.constant;

public enum ResponseStatus {
    SUCCESS(true, 0, "SUCCESS"),

    BOARD_ACCESS_DENIED(false, 1000, "BOARD_ACCESS_DENIED"),
    MEMBER_ACCESS_DENIED(false, 1001, "MEMBER_ACCESS_DENIED"),

    POST_NOT_EXIST(false, 1002, "POST_NOT_EXIST"),

    MEMBER_ID_DUPLICATED(false, 1003, "MEMBER_ID_DUPLICATED"),
    MEMBER_ID_PW_MISMATCH(false, 1004, "MEMBER_ID_PW_MISMATCH"),

    BOARD_URL_NOT_EXIST(false, 1005, "BOARD_URL_NOT_EXIST"),

    METHOD_ARGUMENT_NOT_VALID(false, 1006, "METHOD_ARGUMENT_NOT_VALID"),

    COMMENT_NOT_EXIST(false, 1007, "COMMENT_NOT_EXIST"),

    TOKEN_USER_NOT_FOUND(false, 1008, "TOKEN_USER_NOT_FOUND"),

    TOKEN_NOT_FOUND(false, 1009, "TOKEN_NOT_FOUND"),

    TOKEN_EXPIRED(false, 1010, "TOKEN_EXPIRED"),

    LOGIN_CHECK_FAILED(false, 1011, "LOGIN_CHECK_FAILED");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    ResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
