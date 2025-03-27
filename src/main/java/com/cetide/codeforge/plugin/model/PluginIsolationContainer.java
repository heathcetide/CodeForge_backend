package com.cetide.codeforge.plugin.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class PluginIsolationContainer {

    private final Path pluginFilePath;

    private final Path workDir;

    public PluginIsolationContainer(Path pluginFilePath, Path workDir) {
        this.pluginFilePath = pluginFilePath;
        this.workDir = workDir;
    }

    public Class<?> loadPluginClass(String mainClass) throws ClassNotFoundException, MalformedURLException {
        // 加载插件的主类
        URL[] urls = { pluginFilePath.toUri().toURL() };
        URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
        return classLoader.loadClass(mainClass);
    }

    @Override
    public String toString() {
        return "PluginIsolationContainer{" +
                "pluginFilePath=" + pluginFilePath +
                ", workDir=" + workDir +
                '}';
    }
}
