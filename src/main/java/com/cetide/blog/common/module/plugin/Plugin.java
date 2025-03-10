package com.cetide.blog.common.module.plugin;

import java.util.HashMap;
import java.util.Map;

public interface Plugin {
    /**
     * 插件基本信息
     * @return 插件ID
     */
    String getId();
    /**
     * 插件元信息
     * @return 插件名称
     */
    String getName();
    /**
     * 插件版本
     * @return 插件版本
     */
    String getVersion();
    /**
     * 生命周期管理
     * @param context 上下文
     */
    void initialize(PluginContext context);
    /**
     * 启动插件
     */
    void start();
    /**
     * 停止插件
     */
    void stop();
    /**
     * 扩展点注册
     * @param registry 扩展点注册器
     */
    default void registerExtensions(ExtensionRegistry registry) {}
    /**
     * 配置管理
     * @return 默认配置
     */
    default Map<String, Object> getDefaultConfig() { return new HashMap<>(); }
}