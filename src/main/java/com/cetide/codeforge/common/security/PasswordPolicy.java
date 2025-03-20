package com.cetide.codeforge.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "security.password")
public class PasswordPolicy {

    /**
     * 最短密码长度
     */
    private int minLength = 8;

    /**
     * 最长密码长度
     */
    private int maxLength = 20;

    /**
     * 密码必须包含大写字母
     */
    private boolean requireUppercase = true;

    /**
     * 密码必须包含小写字母
     */
    private boolean requireLowercase = true;

    /**
     * 密码必须包含小数字
     */
    private boolean requireDigit = true;

    /**
     * 密码必须包含特殊字符
     */
    private boolean requireSpecial = true;

    /**
     * 密码重复字符数
     */
    private int maxRepeatedChars = 3;

    /**
     * 历史密码
     */
    private int historySize = 5;

    /**
     * 过期天数
     */
    private int expirationDays = 90;

    private String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isRequireUppercase() {
        return requireUppercase;
    }

    public void setRequireUppercase(boolean requireUppercase) {
        this.requireUppercase = requireUppercase;
    }

    public boolean isRequireLowercase() {
        return requireLowercase;
    }

    public void setRequireLowercase(boolean requireLowercase) {
        this.requireLowercase = requireLowercase;
    }

    public boolean isRequireDigit() {
        return requireDigit;
    }

    public void setRequireDigit(boolean requireDigit) {
        this.requireDigit = requireDigit;
    }

    public boolean isRequireSpecial() {
        return requireSpecial;
    }

    public void setRequireSpecial(boolean requireSpecial) {
        this.requireSpecial = requireSpecial;
    }

    public int getMaxRepeatedChars() {
        return maxRepeatedChars;
    }

    public void setMaxRepeatedChars(int maxRepeatedChars) {
        this.maxRepeatedChars = maxRepeatedChars;
    }

    public int getHistorySize() {
        return historySize;
    }

    public void setHistorySize(int historySize) {
        this.historySize = historySize;
    }

    public int getExpirationDays() {
        return expirationDays;
    }

    public void setExpirationDays(int expirationDays) {
        this.expirationDays = expirationDays;
    }

    public String getSpecialChars() {
        return specialChars;
    }

    public void setSpecialChars(String specialChars) {
        this.specialChars = specialChars;
    }
}