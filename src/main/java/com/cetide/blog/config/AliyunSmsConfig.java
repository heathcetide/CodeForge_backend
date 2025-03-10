package com.cetide.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author bonss
 */
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunSmsConfig {

    /**
     *
     */
    private String smsAccessKeyId;

    /**
     *
     */
    private String smsAccessKeySecret;

    /**
     *
     */
    private String smsTemplateCode;

    /**
     *
     */
    private String smsSignName;

    /**
     * 发送短信最小间隔
     */
    private Integer smsMinInterval;

    /**
     * 是否开启短信发送,默认关闭
     */
    private boolean enable = false;

    public String getSmsAccessKeyId() {
        return smsAccessKeyId;
    }

    public void setSmsAccessKeyId(String smsAccessKeyId) {
        this.smsAccessKeyId = smsAccessKeyId;
    }

    public String getSmsAccessKeySecret() {
        return smsAccessKeySecret;
    }

    public void setSmsAccessKeySecret(String smsAccessKeySecret) {
        this.smsAccessKeySecret = smsAccessKeySecret;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName;
    }

    public Integer getSmsMinInterval() {
        return smsMinInterval;
    }

    public void setSmsMinInterval(Integer smsMinInterval) {
        this.smsMinInterval = smsMinInterval;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
