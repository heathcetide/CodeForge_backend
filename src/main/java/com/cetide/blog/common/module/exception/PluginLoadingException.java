package com.cetide.blog.common.module.exception;


public class PluginLoadingException extends RuntimeException {
    public PluginLoadingException(String message) {
        super(message);
    }

    public PluginLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}