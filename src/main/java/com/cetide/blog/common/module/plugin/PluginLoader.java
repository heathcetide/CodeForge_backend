package com.cetide.blog.common.module.plugin;

import com.cetide.blog.common.module.exception.PluginLoadingException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PluginLoader {

    private final Map<String, Plugin> plugins = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;

    private final Path pluginsDirectory;

    public PluginLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.pluginsDirectory = Paths.get("plugins");
    }

    public void loadPlugins() throws IOException {
        try (var paths = Files.list(pluginsDirectory)) {
            paths.filter(path -> path.toString().endsWith(".jar"))
                    .forEach(this::loadPlugin);
        }
    }

    public Plugin loadPlugin(Path jarPath) {
        try {
            URL[] urls = new URL[]{jarPath.toUri().toURL()};
            PluginClassLoader classLoader = new PluginClassLoader(urls, getClass().getClassLoader());
            ServiceLoader<Plugin> serviceLoader = ServiceLoader.load(Plugin.class, classLoader);

            for (Plugin plugin : serviceLoader) {
                plugins.put(plugin.getId(), plugin);
                return plugin;  // 确保返回加载的插件
            }
        } catch (IOException e) {
            throw new PluginLoadingException("Failed to load plugin: " + jarPath, e);
        }
        return null;  // 如果没有插件被加载，返回 null
    }

    public Optional<Plugin> getPlugin(String pluginId) {
        return Optional.ofNullable(plugins.get(pluginId));
    }

    public void unloadPlugin(String pluginId) {
        plugins.remove(pluginId);
    }
}