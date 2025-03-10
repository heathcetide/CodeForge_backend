package com.cetide.blog.common.module.plugin;

import org.springframework.context.ApplicationContext;

import java.nio.file.Path;
import java.util.Map;

/**
 * 插件上下文
 */
public class PluginContext {

    /**
     * Spring ApplicationContext
     */
    private final ApplicationContext applicationContext;

    /**
     * 插件数据目录
     */
    private final Path pluginDataPath;

    /**
     * 插件配置
     */
    private final Map<String, Object> config;

    public PluginContext(ApplicationContext applicationContext, Path pluginDataPath, Map<String, Object> config) {
        this.applicationContext = applicationContext;
        this.pluginDataPath = pluginDataPath;
        this.config = config;
    }

    public <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    public Path getDataPath() {
        return pluginDataPath;
    }

    public Object getConfigValue(String key) {
        return config.get(key);
    }
}