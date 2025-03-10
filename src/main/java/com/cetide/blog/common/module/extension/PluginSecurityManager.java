package com.cetide.blog.common.module.extension;

import java.io.FilePermission;
import java.security.Permission;
import java.util.Arrays;

/**
 * 插件安全管理器
 */
public class PluginSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        if (isPluginCode()) {
            // 限制插件权限
            if (perm instanceof FilePermission) {
                throw new SecurityException("File access denied");
            }
            if (perm instanceof RuntimePermission) {
                throw new SecurityException("Runtime permission denied");
            }
        }
    }

    private boolean isPluginCode() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .anyMatch(e -> e.getClassName().startsWith("plugins."));
    }
}