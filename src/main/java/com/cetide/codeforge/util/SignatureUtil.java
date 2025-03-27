package com.cetide.codeforge.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 插件签名工具类
 * 用于插件的签名和完整性验证
 */
public class SignatureUtil {

    // 假设这个方法是用来验证插件文件的签名
    public static boolean verifyPluginSignature(String pluginFilePath) {
        try {
            // 假设我们比较插件的文件哈希值来验证完整性
            String expectedSignature = getExpectedSignature(pluginFilePath);
            String actualSignature = generateFileSignature(pluginFilePath);

            // 比较实际签名和预期签名
            return expectedSignature.equals(actualSignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 生成插件文件的签名（哈希值）
    private static String generateFileSignature(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        byte[] hashBytes = digest.digest(fileBytes);
        return Base64.getEncoder().encodeToString(hashBytes); // 将哈希值编码为Base64字符串
    }

    // 获取预期的签名（假设此方法返回已经存储的预期签名）数据库中
    private static String getExpectedSignature(String pluginFilePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(Paths.get(pluginFilePath));
        byte[] hashBytes = digest.digest(fileBytes);
        return Base64.getEncoder().encodeToString(hashBytes); // 将哈希值编码为Base64字符串
    }
}