package com.cetide.codeforge.plugin.repository;

import com.cetide.codeforge.plugin.model.PluginInfo;

import java.util.Optional;

/**
 * 插件注册器
 *
 * @author heathcetide
 */
public interface PluginRepository {

    /**
     * 注册插件
     */
    void register(PluginInfo plugin);

    /**
     * 卸载插件
     */
    void unregister(String pluginId);

    /**
     * 根据id获取插件
     */
    Optional<PluginInfo> get(String pluginId);
}