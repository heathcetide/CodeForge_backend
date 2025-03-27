package com.cetide.codeforge.plugin.core;

/**
 * 解析和验证插件的依赖关系
 *
 * @author heathcetide
 */
public class DependencyResolver {
    public static boolean isCompatible(String requiredVersion, String installedVersion) {
        // 简单的版本兼容性检查，实际情况可能更复杂
        return requiredVersion.equals(installedVersion);
    }
}
