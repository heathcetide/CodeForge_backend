package com.cetide.blog.common.module.example;

public class LogExtensionImpl implements LogExtension {
    @Override
    public void log(String message) {
        System.out.println("Log from My Plugin: " + message);
    }
}
