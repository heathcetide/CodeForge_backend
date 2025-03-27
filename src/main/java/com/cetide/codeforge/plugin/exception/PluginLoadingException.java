package com.cetide.codeforge.plugin.exception;

/**
 * 插件加载异常
 */
public class PluginLoadingException extends RuntimeException {

    public PluginLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginLoadingException(String message) {
        super(message);
    }
}