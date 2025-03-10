package com.cetide.blog.common.module.plugin;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// PluginRepository.java - 插件仓库
@Component
public class PluginRepository {
    private final Map<String, PluginInfo> pluginRegistry = new ConcurrentHashMap<>();

    public void registerPlugin(PluginInfo info) {
        pluginRegistry.put(info.getId(), info);
    }

    public Optional<PluginInfo> getPlugin(String pluginId) {
        return Optional.ofNullable(pluginRegistry.get(pluginId));
    }

    public void removePlugin(String pluginId) {
        pluginRegistry.remove(pluginId);
    }

    public List<PluginInfo> getAllPlugins() {
        return pluginRegistry.values().stream()
                .collect(Collectors.toList());
    }

    public void updatePluginStatus(String pluginId, PluginStatus status) {
        PluginInfo info = pluginRegistry.get(pluginId);
        if (info != null) {
            info.setStatus(status);
        }
    }

    public boolean hasPlugin(String pluginId) {
        return pluginRegistry.containsKey(pluginId);
    }

    public void updatePluginMetadata(String pluginId, Map<String, Object> metadata) {
        PluginInfo info = pluginRegistry.get(pluginId);
        if (info != null) {
            info.setMetadata(metadata);
        }
    }
}

/**
 * 存储插件元数据
 * 管理插件状态
 * 处理插件版本
 */