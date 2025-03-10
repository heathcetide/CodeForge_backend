package com.cetide.blog.exception;

public class RateLimitExceededException extends RuntimeException {
    private final Integer code;

    public RateLimitExceededException(String message) {
        super(message);
        this.code = 500;
    }

    public RateLimitExceededException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
