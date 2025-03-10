package com.cetide.blog.common.module.extension;

import com.cetide.blog.common.module.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HotDeployWatcher {
    private final PluginManager pluginManager;

    @Autowired
    public HotDeployWatcher(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @PostConstruct
    public void startWatching() {
        // 实现文件监控逻辑
    }
}