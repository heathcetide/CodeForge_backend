package com.cetide.codeforge.exception;

public class AccountLockedException extends RuntimeException {
    private final Integer code;

    public AccountLockedException(String message) {
        super(message);
        this.code = 500;
    }

    public AccountLockedException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}