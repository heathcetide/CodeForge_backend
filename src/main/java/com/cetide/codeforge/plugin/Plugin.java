package com.cetide.codeforge.plugin;

import com.cetide.codeforge.plugin.enums.PluginState;

import java.util.HashMap;
import java.util.Map;

public class Plugin {
    private String id;
    private PluginState state;
    private String version;

    public String getId() { return id; }
    public PluginState getState() { return state; }
    public void setState(PluginState state) { this.state = state; }
    public String getVersion() { return version; }

    // 获取插件的默认配置
    public Map<String, Object> getDefaultConfig() {
        // 返回插件的默认配置
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return "Plugin{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", version='" + version + '\'' +
                '}';
    }
}