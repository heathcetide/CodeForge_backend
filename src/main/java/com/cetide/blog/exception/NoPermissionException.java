package com.cetide.blog.exception;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message) {
        super(message);
    }
}
