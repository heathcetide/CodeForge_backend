package com.cetide.codeforge.controller;

import com.cetide.codeforge.plugin.Plugin;
import com.cetide.codeforge.plugin.PluginMeta;
import com.cetide.codeforge.plugin.core.PluginLoader;
import com.cetide.codeforge.plugin.core.PluginSecurityManager;
import com.cetide.codeforge.plugin.exception.PluginLoadingException;
import com.cetide.codeforge.plugin.model.PluginIsolationContainer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping("/api/plugin")
@RestController
public class PluginController {

    private final PluginSecurityManager securityManager;

    private final PluginLoader pluginLoader;

    public PluginController(PluginSecurityManager securityManager, PluginLoader pluginLoader) {
        this.securityManager = securityManager;
        this.pluginLoader = pluginLoader;
    }


    @PostMapping
    public String uploadJar(@RequestPart("file") MultipartFile pluginFile) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 步骤1：存储临时文件
        Path tempFile = Files.createTempFile("plugin-", ".jar"); //创建一个以plugin-为前缀, .jar为后缀的文件
        pluginFile.transferTo(tempFile); //将内容写入目前文件

        // 步骤2：验证签名
        if (!securityManager.verifySignature(tempFile)) {
            throw new PluginLoadingException("插件签名验证失败");
        }

        // 步骤3：解析元数据
        PluginMeta meta = pluginLoader.parsePluginMeta(tempFile);
        System.out.println(meta);

        // 步骤4：检查依赖

        // 步骤5：创建隔离环境
        Path workDir = createWorkDirectory(meta.getId());
        PluginIsolationContainer container = new PluginIsolationContainer(tempFile, workDir);
        System.out.println(container);

        // 步骤6：加载主类
        Class<?> pluginClass = container.loadPluginClass(meta.getMainClass());
        Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
        System.out.println(plugin);
        return tempFile.toString();
    }

    private Path createWorkDirectory(String pluginId) throws IOException {
        // 创建一个以插件ID为名称的工作目录
        Path workDir = Files.createTempDirectory(pluginId);
        Files.createDirectories(workDir); // 确保目录存在
        return workDir;
    }
}
