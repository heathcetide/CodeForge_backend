package com.cetide.codeforge.plugin.interfaces;

import com.cetide.codeforge.plugin.enums.PluginState;

import java.util.Map;

/**
 * 插件接口，定义插件的基础功能和生命周期。
 */
public interface Plugin {

    // 获取插件的唯一标识符
    String getId();

    // 获取插件的状态
    PluginState getState();
    void setState(PluginState state);

    // 获取插件的版本号
    String getVersion();

    // 获取插件的默认配置
    Map<String, Object> getDefaultConfig();

    // 初始化插件（加载资源等）
    void initialize(Map<String, Object> config);

    // 启动插件
    void start();

    // 停止插件
    void stop();

    // 清理插件资源
    void cleanup();

    // 插件的运行时状态检查
    boolean isRunning();

    // 插件的日志记录方法
    void log(String message);

    // 插件执行的主功能（可以根据需求自行定义）
    void execute();
}