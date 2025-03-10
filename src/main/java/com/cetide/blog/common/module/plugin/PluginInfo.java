package com.cetide.blog.common.module.plugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

// PluginInfo.java - 插件信息
public class PluginInfo {
    private String id;          // 插件ID
    private String name;        // 插件名称
    private String version;     // 插件版本
    private PluginStatus status; // 插件状态
    private Path location;      // 插件位置
    private Map<String, Object> metadata; // 插件元数据

    public PluginInfo(String id, String name, String version, PluginStatus status, Path location) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.status = status;
        this.location = location;
        this.metadata = new HashMap<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PluginStatus getStatus() {
        return status;
    }

    public void setStatus(PluginStatus status) {
        this.status = status;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}