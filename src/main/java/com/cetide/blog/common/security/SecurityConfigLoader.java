package com.cetide.blog.common.security;

import com.cetide.blog.exception.PasswordPolicyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 安全配置加载器
 * 在应用启动时加载和验证安全配置
 * 
 * @author yourname
 * @since 1.0.0
 */
@Component
public class SecurityConfigLoader {
    private final SecurityProperties securityProperties;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecurityConfigLoader(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStarted() {
        validateSecurityConfig();
        logSecurityConfig();
    }
    
    /**
     * 验证安全配置的有效性
     */
    private void validateSecurityConfig() {
        // 验证密码配置
        if (securityProperties.getPassword().getMinLength() > securityProperties.getPassword().getMaxLength()) {
            throw new IllegalStateException("密码最小长度不能大于最大长度");
        }
        
        // 验证加密配置
        if (securityProperties.getEncryption().getKey() == null || 
            securityProperties.getEncryption().getKey().length() < 16) {
            throw new IllegalStateException("加密密钥不能为空且长度不能小于16位");
        }
        
        // 验证JWT配置
        if (!StringUtils.hasText(jwtSecret) || jwtSecret.length() < 32) {
            throw new IllegalStateException("JWT密钥不能为空且长度不能小于32位");
        }

        System.out.println("安全配置验证通过");
    }
    
    /**
     * 记录安全配置信息（不包含敏感信息）
     */
    private void logSecurityConfig() {
//        log.info("密码配置: minLength={}, maxLength={}, requireUppercase={}, requireLowercase={}, " +
//                "requireDigit={}, requireSpecial={}, maxRepeatedChars={}, historySize={}, expirationDays={}",
//            securityProperties.getPassword().getMinLength(),
//            securityProperties.getPassword().getMaxLength(),
//            securityProperties.getPassword().isRequireUppercase(),
//            securityProperties.getPassword().isRequireLowercase(),
//            securityProperties.getPassword().isRequireDigit(),
//            securityProperties.getPassword().isRequireSpecial(),
//            securityProperties.getPassword().getMaxRepeatedChars(),
//            securityProperties.getPassword().getHistorySize(),
//            securityProperties.getPassword().getExpirationDays()
//        );
//
//        log.info("登录配置: maxAttempts={}, lockDuration={}, enableCaptcha={}, captchaExpiration={}",
//            securityProperties.getLogin().getMaxAttempts(),
//            securityProperties.getLogin().getLockDuration(),
//            securityProperties.getLogin().isEnableCaptcha(),
//            securityProperties.getLogin().getCaptchaExpiration()
//        );
//
//        log.info("加密配置: algorithm={}, mode={}, padding={}",
//            securityProperties.getEncryption().getAlgorithm(),
//            securityProperties.getEncryption().getMode(),
//            securityProperties.getEncryption().getPadding()
//        );
//
//        log.info("JWT配置: expiration={}, issuer={}, header={}, tokenPrefix={}",
//            securityProperties.getJwt().getExpiration(),
//            securityProperties.getJwt().getIssuer(),
//            securityProperties.getJwt().getHeader(),
//            securityProperties.getJwt().getTokenPrefix()
//        );
    }

    // 添加密码策略验证
    private void validatePasswordComplexity(String password) {
        if (password.length() < securityProperties.getPassword().getMinLength()) {
            throw new PasswordPolicyException("密码长度不足");
        }
        if (securityProperties.getPassword().isRequireUppercase() && 
            !password.matches(".*[A-Z].*")) {
            throw new PasswordPolicyException("必须包含大写字母");
        }

    }
} 