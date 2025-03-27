package com.cetide.codeforge.plugin;

import java.util.Map;

/**
 * 插件元数据
 *
 * @author heathcetide
 */
public class PluginMeta {

    /**
     * 插件id
     */
    private String id;

    /**
     * 插件名
     */
    private String name;

    /**
     * 插件版本
     */
    private String version;

    /**
     * 主类名
     */
    private String mainClass;

    /**
     * 插件依赖
     */
    private Map<String, String> dependencies;

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

    public Map<String, String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Map<String, String> dependencies) {
        this.dependencies = dependencies;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public String toString() {
        return "PluginMeta{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", dependencies=" + dependencies +
                '}';
    }
}