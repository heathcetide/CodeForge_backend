package com.cetide.codeforge.plugin.enums;

/**
 * 插件生命周期状态枚举
 *
 * @author heathcetide
 */
public enum PluginState {

    /**
     * 已安装
     */
    INSTALLED,

    /**
     * 依赖已解析
     */
    RESOLVED,

    /**
     * 启动中
     */
    STARTING,

    /**
     * 运行中
     */
    ACTIVE,

    /**
     * 停止中
     */
    STOPPING,

    /**
     * 已卸载
     */
    UNINSTALLED
}