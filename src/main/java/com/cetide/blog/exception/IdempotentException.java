package com.cetide.blog.exception;

public class IdempotentException extends RuntimeException {
    private final Integer code;

    public IdempotentException(String message) {
        super(message);
        this.code = 500;
    }

    public IdempotentException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}