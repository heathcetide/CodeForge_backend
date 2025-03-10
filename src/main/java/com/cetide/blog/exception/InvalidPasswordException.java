package com.cetide.blog.exception;

public class InvalidPasswordException extends RuntimeException {
    private final Integer code;

    public InvalidPasswordException(String message) {
        super(message);
        this.code = 500;
    }

    public InvalidPasswordException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}