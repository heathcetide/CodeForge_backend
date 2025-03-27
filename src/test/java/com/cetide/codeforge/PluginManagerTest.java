package com.cetide.codeforge;

import cn.hutool.core.exceptions.DependencyException;
import com.cetide.codeforge.plugin.exception.PluginDependencyException;
import com.cetide.codeforge.plugin.exception.PluginLoadingException;
import com.cetide.codeforge.plugin.exception.PluginSecurityException;
import com.cetide.codeforge.plugin.exception.SignatureException;
import com.cetide.codeforge.plugin.model.PluginInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class PluginManagerTest {

    public PluginInfo installPlugin(MultipartFile pluginFile) throws PluginLoadingException {
        try {
            // 步骤1：存储临时文件
            Path tempFile = Files.createTempFile("plugin-", ".jar"); //创建一个以plugin-为前缀, .jar为后缀的文件
            pluginFile.transferTo(tempFile); //将内容写入目前文件
        } catch (SignatureException e) {
            throw new PluginSecurityException("签名验证失败", e);
        } catch (DependencyException e) {
            throw new PluginDependencyException("依赖检查失败", e);
        } catch (Exception e) {
            throw new PluginLoadingException("插件安装失败", e);
        }
        return null;
    }
}
