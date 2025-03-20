package com.cetide.codeforge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author bonss
 */
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
    /**
     * 微信appId
     */
    private String appId;

    /**
     * 微信appSecret
     */
    private String appSecret;

    /**
     * 微信订阅消息模板Id
     */
    private String templateId;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
