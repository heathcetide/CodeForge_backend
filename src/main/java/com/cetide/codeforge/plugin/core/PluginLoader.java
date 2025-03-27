package com.cetide.codeforge.plugin.core;

import com.cetide.codeforge.plugin.PluginMeta;
import com.cetide.codeforge.plugin.exception.PluginLoadingException;
import com.cetide.codeforge.plugin.model.PluginInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件加载接口
 *
 * @author heathcetide
 */
@Component
public class PluginLoader {

    /**
     * 加载插件
     */
    PluginInfo load(byte[] pluginBytes) throws PluginLoadingException {
        return null;
    }

    /**
     * Unload the plugin
     */
    void unload(String pluginId) {
        // Implement logic to unload or cleanup plugin resources, e.g., remove classes from the class loader
        // You can use a custom class loader to unload the plugin if needed.
        System.out.println("Unloading plugin with ID: " + pluginId);
    }

    public PluginMeta parsePluginMeta(Path pluginFilePath) throws IOException {
        // 假设插件的元数据在 JAR 文件中的 plugin-meta.json 文件里
        try (JarFile jarFile = new JarFile(pluginFilePath.toFile())) {
            JarEntry metaEntry = jarFile.getJarEntry("plugin-meta.json");
            if (metaEntry == null) {
                throw new IOException("插件元数据文件不存在");
            }

            try (InputStream inputStream = jarFile.getInputStream(metaEntry)) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(inputStream, PluginMeta.class);
            }
        }
    }
}