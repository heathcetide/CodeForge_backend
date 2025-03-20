package com.cetide.codeforge.common.encrypt;//package org.cetide.codeforge.infrastructure.service;
//
//import org.cetide.codeforge.infrastructure.service.PasswordPolicyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PasswordService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final PasswordPolicyService passwordPolicyService;
//    private final UserService userService; // 用户服务，负责查询和更新用户信息
//
//    @Autowired
//    public PasswordService(PasswordEncoder passwordEncoder,
//                           PasswordPolicyService passwordPolicyService,
//                           UserService userService) {
//        this.passwordEncoder = passwordEncoder;
//        this.passwordPolicyService = passwordPolicyService;
//        this.userService = userService;
//    }
//
//    /**
//     * 校验原始密码是否符合安全策略要求
//     *
//     * @param password 原始密码
//     * @return 如果符合安全策略返回 true，否则返回 false
//     */
//    public boolean validatePassword(String password) {
//        return passwordPolicyService.validatePassword(password);
//    }
//
//    /**
//     * 使用 PasswordEncoder 对原始密码进行加密
//     *
//     * @param rawPassword 原始密码
//     * @return 加密后的密码
//     */
//    public String encodePassword(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }
//
//    /**
//     * 校验原始密码与存储的加密密码是否匹配
//     *
//     * @param rawPassword     原始密码
//     * @param encodedPassword 存储的加密密码
//     * @return 匹配返回 true，否则返回 false
//     */
//    public boolean checkPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//
//    /**
//     * 修改用户密码
//     * <p>
//     * 该方法将执行以下操作：
//     * <ol>
//     *     <li>通过 UserService 获取当前用户的加密密码</li>
//     *     <li>校验旧密码是否匹配</li>
//     *     <li>使用 PasswordPolicyService 校验新密码是否符合安全策略</li>
//     *     <li>使用 PasswordEncoder 对新密码进行加密</li>
//     *     <li>通过 UserService 更新用户密码</li>
//     * </ol>
//     *
//     * @param userIdentifier 用户标识（例如用户名或邮箱）
//     * @param oldPassword    用户当前原始密码
//     * @param newPassword    用户输入的新原始密码
//     * @return 如果密码修改成功返回 true，否则返回 false
//     */
//    public boolean changePassword(String userIdentifier, String oldPassword, String newPassword) {
//        // 获取当前用户的加密密码
//        String currentEncodedPassword = userService.getPasswordByUserIdentifier(userIdentifier);
//        // 校验旧密码是否正确
//        if (!passwordEncoder.matches(oldPassword, currentEncodedPassword)) {
//            return false;
//        }
//        // 校验新密码是否符合安全策略
//        if (!passwordPolicyService.validatePassword(newPassword)) {
//            return false;
//        }
//        // 对新密码进行加密，并更新用户记录
//        String newEncodedPassword = passwordEncoder.encode(newPassword);
//        return userService.updatePassword(userIdentifier, newEncodedPassword);
//    }
//}
