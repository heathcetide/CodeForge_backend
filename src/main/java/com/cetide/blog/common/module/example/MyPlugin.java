package com.cetide.blog.common.module.example;

import com.cetide.blog.common.module.plugin.ExtensionRegistry;
import com.cetide.blog.common.module.plugin.Plugin;
import com.cetide.blog.common.module.plugin.PluginContext;

public class MyPlugin implements Plugin {
    private String id = "myPlugin";
    private String name = "My Custom Plugin";
    private String version = "1.0.0";

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void initialize(PluginContext context) {
        String configValue = (String) context.getConfigValue("myConfigKey");
        System.out.println("Loaded config value: " + configValue);
    }

    @Override
    public void start() {
        // Start plugin custom logic here
    }

    @Override
    public void stop() {
        // Stop plugin custom logic here
    }

    @Override
    public void registerExtensions(ExtensionRegistry registry) {
        registry.registerExtension(LogExtension.class, new LogExtensionImpl());
    }
}
