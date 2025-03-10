package com.cetide.blog.common.module;

import com.cetide.blog.common.module.exception.PluginLoadingException;
import com.cetide.blog.common.module.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

/**
 * PluginManager.java - 插件管理器
 */
@Service
public class PluginManager {

    private static final Logger log = LoggerFactory.getLogger(PluginManager.class);
    private final Path pluginsDirectory;
    private final PluginLoader pluginLoader;
    private final PluginRepository pluginRepository;
    private final ApplicationContext applicationContext;

    @Autowired
    public PluginManager(PluginLoader pluginLoader, 
                        PluginRepository pluginRepository, 
                        ApplicationContext applicationContext) {
        this.pluginLoader = pluginLoader;
        this.pluginRepository = pluginRepository;
        this.applicationContext = applicationContext;
        this.pluginsDirectory = Paths.get("plugins");
        initializePluginsDirectory();
    }

    private void initializePluginsDirectory() {
        try {
            if (!Files.exists(pluginsDirectory)) {
                Files.createDirectories(pluginsDirectory);
            }
        } catch (IOException e) {
            throw new PluginLoadingException("Failed to create plugins directory", e);
        }
    }

    public PluginInfo installPlugin(MultipartFile pluginFile) {
        try {
            // 保存插件文件
            Path targetPath = pluginsDirectory.resolve(pluginFile.getOriginalFilename());
            Files.copy(pluginFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 加载插件
            Plugin plugin = pluginLoader.loadPlugin(targetPath);  // 返回 Plugin 类型

            // 创建插件信息
            PluginInfo pluginInfo = new PluginInfo(
                    plugin.getId(),
                    plugin.getName(),
                    plugin.getVersion(),
                    PluginStatus.INSTALLED,
                    targetPath
            );

            // 注册插件
            pluginRepository.registerPlugin(pluginInfo);

            // 初始化并启动插件
            plugin.initialize(new PluginContext(applicationContext, targetPath, plugin.getDefaultConfig()));  // 传入必要的参数
            plugin.start();

            return pluginInfo;
        } catch (IOException e) {
            throw new PluginLoadingException("Failed to install plugin", e);
        }
    }


    public void uninstallPlugin(String pluginId) {
        Optional<PluginInfo> pluginInfo = pluginRepository.getPlugin(pluginId);
        if (pluginInfo.isPresent()) {
            Optional<Plugin> pluginOptional = getPlugin(pluginId);
            Plugin plugin = pluginOptional.orElse(null);
            if (plugin != null) {
                // 停止插件
                plugin.stop();
                // 从加载器中移除
                pluginLoader.unloadPlugin(pluginId);
            }

            try {
                // 删除插件文件
                Files.deleteIfExists(pluginInfo.get().getLocation());
                // 从仓库中移除
                pluginRepository.removePlugin(pluginId);
            } catch (IOException e) {
                throw new PluginLoadingException("Failed to delete plugin file", e);
            }
        }
    }

    public List<PluginInfo> getInstalledPlugins() {
        return pluginRepository.getAllPlugins();
    }

    public Optional<Plugin> getPlugin(String pluginId) {
        return pluginLoader.getPlugin(pluginId);
    }

    public void enablePlugin(String pluginId) {
        Optional<Plugin> pluginOptional = getPlugin(pluginId);
        pluginOptional.ifPresent(plugin -> {
            plugin.start();
            pluginRepository.updatePluginStatus(pluginId, PluginStatus.ENABLED);
        });
    }

    public void disablePlugin(String pluginId) {
        Optional<Plugin> pluginOptional = getPlugin(pluginId);
        pluginOptional.ifPresent(plugin -> {
            plugin.stop();
            pluginRepository.updatePluginStatus(pluginId, PluginStatus.DISABLED);
        });
    }
}

/**
 * 插件的安装
 * 插件的卸载
 * 插件状态管理
 * 插件验证
 */