package com.cetide.codeforge.common.encrypt;//package org.cetide.codeforge.infrastructure.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.util.Base64;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class PasswordResetService {
//
//    // Token 有效期：15 分钟（单位：毫秒）
//    private static final long TOKEN_VALIDITY_MILLIS = 15 * 60 * 1000;
//
//    // 内存存储，保存 token 与对应的重置信息（用户标识、过期时间）
//    private final Map<String, ResetTokenInfo> tokenStorage = new ConcurrentHashMap<>();
//    private final SecureRandom secureRandom = new SecureRandom();
//
//    // 假设存在 UserService 用于更新用户密码（此处需自行实现）
//    private final UserService userService;
//    // 注入 PasswordEncoder 对密码进行加密
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public PasswordResetService(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    /**
//     * 为指定用户生成密码重置 token
//     *
//     * @param userIdentifier 用户标识，如用户名或邮箱
//     * @return 生成的 token 字符串
//     */
//    public String generateResetToken(String userIdentifier) {
//        // 生成随机 token
//        byte[] randomBytes = new byte[24];
//        secureRandom.nextBytes(randomBytes);
//        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
//        // 保存 token 及其过期时间
//        ResetTokenInfo tokenInfo = new ResetTokenInfo(userIdentifier, System.currentTimeMillis() + TOKEN_VALIDITY_MILLIS);
//        tokenStorage.put(token, tokenInfo);
//        return token;
//    }
//
//    /**
//     * 校验 token 是否有效，并返回关联的用户标识
//     *
//     * @param token 重置密码的 token
//     * @return 用户标识；若 token 无效则返回 null
//     */
//    public String validateResetToken(String token) {
//        ResetTokenInfo tokenInfo = tokenStorage.get(token);
//        if (tokenInfo == null) {
//            return null;
//        }
//        // 检查是否过期
//        if (System.currentTimeMillis() > tokenInfo.getExpiryTime()) {
//            tokenStorage.remove(token);
//            return null;
//        }
//        return tokenInfo.getUserIdentifier();
//    }
//
//    /**
//     * 根据 token 重置用户密码
//     *
//     * @param token       重置密码的 token
//     * @param newPassword 用户输入的新密码（会进行加密处理）
//     * @return 重置成功返回 true，否则返回 false
//     */
//    public boolean resetPassword(String token, String newPassword) {
//        String userIdentifier = validateResetToken(token);
//        if (userIdentifier == null) {
//            return false;
//        }
//        // 对新密码进行加密处理
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        // 通过 UserService 更新用户密码（此处需实现具体的业务逻辑）
//        boolean updateSuccess = userService.updatePassword(userIdentifier, encodedPassword);
//        if (updateSuccess) {
//            // 重置成功后，移除 token 避免重复使用
//            tokenStorage.remove(token);
//        }
//        return updateSuccess;
//    }
//
//    // 内部类，用于存储 token 相关信息
//    private static class ResetTokenInfo {
//        private final String userIdentifier;
//        private final long expiryTime;
//
//        public ResetTokenInfo(String userIdentifier, long expiryTime) {
//            this.userIdentifier = userIdentifier;
//            this.expiryTime = expiryTime;
//        }
//
//        public String getUserIdentifier() {
//            return userIdentifier;
//        }
//
//        public long getExpiryTime() {
//            return expiryTime;
//        }
//    }
//}
