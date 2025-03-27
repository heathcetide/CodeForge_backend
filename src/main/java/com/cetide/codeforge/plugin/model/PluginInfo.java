package com.cetide.codeforge.plugin.model;

import com.cetide.codeforge.plugin.enums.PluginState;

import java.nio.file.Path;

/**
 * 插件信息
 *
 * @author heathcetide
 */
public class PluginInfo {

    /**
     * id
     */
    private String id;

    /**
     * 版本
     */
    private String version;

    /**
     * 插件生命周期状态
     */
    private PluginState state;

    /**
     * 工作目录
     */
    private Path workDir;

    public PluginInfo(String id, String version) {
        this.id = id;
        this.version = version;
        this.state = PluginState.INSTALLED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PluginState getState() {
        return state;
    }

    public void setState(PluginState state) {
        this.state = state;
    }

    public Path getWorkDir() {
        return workDir;
    }

    public void setWorkDir(Path workDir) {
        this.workDir = workDir;
    }
}