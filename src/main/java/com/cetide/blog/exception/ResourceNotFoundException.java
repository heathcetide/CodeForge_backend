package com.cetide.blog.exception;

public class ResourceNotFoundException  extends RuntimeException {
    private final Integer code;

    public ResourceNotFoundException(String message) {
        super(message);
        this.code = 500;
    }

    public ResourceNotFoundException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}