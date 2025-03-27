package com.cetide.codeforge.plugin.core;

import cn.hutool.core.exceptions.DependencyException;
import com.cetide.codeforge.plugin.Plugin;
import com.cetide.codeforge.plugin.PluginMeta;
import com.cetide.codeforge.plugin.enums.PluginState;
import com.cetide.codeforge.plugin.exception.PluginDependencyException;
import com.cetide.codeforge.plugin.exception.PluginLoadingException;
import com.cetide.codeforge.plugin.exception.PluginSecurityException;
import com.cetide.codeforge.plugin.exception.SignatureException;
import com.cetide.codeforge.plugin.model.PluginInfo;
import com.cetide.codeforge.plugin.model.PluginIsolationContainer;
import com.cetide.codeforge.plugin.repository.PluginRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * 插件管理系统核心类，负责插件的全生命周期管理
 * 主要职责：
 * - 插件的安装、卸载、启用、禁用
 * - 依赖关系管理
 * - 插件状态维护
 * - 资源隔离管理
 * - 安全策略执行
 *
 * @author heathcetide
 */
public class PluginManager {

    private static final Logger log = LoggerFactory.getLogger(PluginManager.class);

    private final PluginLoader pluginLoader;

    private final PluginRepository pluginRepository;

    private final PluginConfigManager configManager;

    private final PluginSecurityManager securityManager;

    private final PluginLifecycleManager lifecycleManager;

    @Autowired
    public PluginManager(PluginLoader pluginLoader,
                         PluginRepository pluginRepository,
                         PluginConfigManager configManager,
                         PluginSecurityManager securityManager,
                         PluginLifecycleManager lifecycleManager) {
        this.pluginLoader = pluginLoader;
        this.pluginRepository = pluginRepository;
        this.configManager = configManager;
        this.securityManager = securityManager;
        this.lifecycleManager = lifecycleManager;
    }

    /**
     * 安装插件主流程
     *
     * @param pluginFile 上传的插件文件（JAR格式）
     * @return 安装后的插件信息
     * <p>
     * 安装步骤：
     * 1. 临时存储上传文件
     * 2. 验证插件签名
     * 3. 解析插件元数据
     * 4. 检查依赖关系
     * 5. 创建隔离环境
     * 6. 加载插件类
     * 7. 初始化配置
     * 8. 注册插件
     * 9. 启动插件
     */
    public PluginInfo installPlugin(MultipartFile pluginFile) throws PluginLoadingException {
        try {
            // 步骤1：存储临时文件
            Path tempFile = Files.createTempFile("plugin-", ".jar");
            pluginFile.transferTo(tempFile);

            // 步骤2：验证签名
            if (!securityManager.verifySignature(tempFile)) {
                throw new PluginLoadingException("插件签名验证失败");
            }

            // 步骤3：解析元数据
            PluginMeta meta = pluginLoader.parsePluginMeta(tempFile);

            // 步骤4：检查依赖
            checkDependencies(meta.getDependencies());

            // 步骤5：创建隔离环境
            Path workDir = createWorkDirectory(meta.getId());
            PluginIsolationContainer container = new PluginIsolationContainer(tempFile, workDir);

            // 步骤6：加载主类
            Class<?> pluginClass = container.loadPluginClass(meta.getMainClass());
            Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

            // 步骤7：初始化配置
            Map<String, Object> defaultConfig = plugin.getDefaultConfig();
            configManager.initializeConfig(meta.getId(), defaultConfig);

            // 步骤8：注册插件
            PluginInfo pluginInfo = new PluginInfo(
                    meta.getId(),
                    meta.getVersion()
            );
            pluginRepository.register(pluginInfo);

            // 步骤9：启动插件
            lifecycleManager.initializePlugin(plugin, configManager.getConfig(meta.getId()));

            return pluginInfo;

        } catch (SignatureException e) {
            throw new PluginSecurityException("签名验证失败", e);
        } catch (DependencyException e) {
            throw new PluginDependencyException("依赖检查失败", e);
        } catch (Exception e) {
            log.error("插件安装失败: {}", e.getMessage());
            throw new PluginLoadingException("插件安装失败", e);
        }
    }

    /**
     * 卸载插件
     * @param pluginId 要卸载的插件ID
     *
     * 卸载流程：
     * 1. 停止插件运行
     * 2. 释放所有资源
     * 3. 删除配置文件
     * 4. 清理工作目录
     * 5. 从注册表中移除
     */
    public void uninstallPlugin(String pluginId) {
        Optional<PluginInfo> pluginOpt = pluginRepository.get(pluginId);
        if (pluginOpt.isPresent()) {
            PluginInfo plugin = pluginOpt.get();

            // 步骤1：停止插件
            lifecycleManager.stopPlugin(pluginId);

            // 步骤2：释放资源
            pluginLoader.unload(pluginId);

            // 步骤3：删除配置
            configManager.deleteConfig(pluginId);

            // 步骤4：清理目录
            try {
                FileUtils.deleteDirectory(plugin.getWorkDir().toFile());
            } catch (IOException e) {
                log.warn("清理插件目录失败: {}", pluginId);
            }

            // 步骤5：移除注册
            pluginRepository.unregister(pluginId);
        }
    }

    private void checkDependencies(Map<String, String> dependencies) {
        dependencies.forEach((pluginId, requiredVersion) -> {
            PluginInfo installed = pluginRepository.get(pluginId)
                    .orElseThrow(() -> new DependencyException("缺少依赖插件: " + pluginId));

            if (!DependencyResolver.isCompatible(requiredVersion, installed.getVersion())) {
                throw new DependencyException("插件版本不兼容: " + pluginId);
            }
        });
    }

    private Path createWorkDirectory(String pluginId) throws IOException {
        // 创建一个以插件ID为名称的工作目录
        Path workDir = Files.createTempDirectory(pluginId);
        Files.createDirectories(workDir); // 确保目录存在
        return workDir;
    }
}
