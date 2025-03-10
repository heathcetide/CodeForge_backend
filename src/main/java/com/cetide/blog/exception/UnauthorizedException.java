package com.cetide.blog.exception;

public class UnauthorizedException extends RuntimeException {
    private final Integer code;

    public UnauthorizedException(String message) {
        super(message);
        this.code = 500;
    }

    public UnauthorizedException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}