package com.cetide.codeforge.plugin.exception;

/**
 * 配置异常
 */
public class ConfigException extends RuntimeException {

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}