package com.cetide.codeforge.plugin.exception;

public class PluginDependencyException extends RuntimeException {

    public PluginDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}