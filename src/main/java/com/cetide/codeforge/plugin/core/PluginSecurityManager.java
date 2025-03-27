package com.cetide.codeforge.plugin.core;

import com.cetide.codeforge.plugin.exception.SignatureException;
import com.cetide.codeforge.plugin.model.PluginInfo;
import com.cetide.codeforge.util.SignatureUtil;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * 插件安全管理服务
 * 提供插件完整性验证等安全功能
 */
@Component
public class PluginSecurityManager {

    /**
     * 验证插件的完整性
     * @param pluginInfo 插件信息
     */
    public void verifyIntegrity(PluginInfo pluginInfo) {
        // 获取插件文件的路径或相关信息
        String pluginId = pluginInfo.getId();
        String pluginVersion = pluginInfo.getVersion();

        // 假设我们通过插件的ID和版本来获取插件的文件路径（具体方法可以根据需求修改）
        String pluginFilePath = getPluginFilePath(pluginId, pluginVersion);

        // 假设 SignatureUtil 提供了验证签名的方法（你可以用实际的加密验证逻辑）
        boolean isValid = SignatureUtil.verifyPluginSignature(pluginFilePath);

        if (!isValid) {
            throw new SecurityException("插件的完整性验证失败，插件可能已被篡改！");
        }
    }

    /**
     * 假设方法：获取插件的文件路径（实际可以根据插件的ID和版本查询数据库或文件系统）
     * @param pluginId 插件ID
     * @param pluginVersion 插件版本
     * @return 插件文件路径
     */
    private String getPluginFilePath(String pluginId, String pluginVersion) {
        // 根据插件ID和版本获取插件文件路径的逻辑（例如文件系统中或数据库中查找）
        return "/path/to/plugins/" + pluginId + "-" + pluginVersion + ".jar"; // 示例路径
    }

    public boolean verifySignature(Path pluginFilePath) {
        try {
            // 假设 SignatureUtil 提供了签名验证方法
            return SignatureUtil.verifyPluginSignature(pluginFilePath.toString());
        } catch (Exception e) {
            throw new SignatureException("签名验证失败", e);
        }
    }
}