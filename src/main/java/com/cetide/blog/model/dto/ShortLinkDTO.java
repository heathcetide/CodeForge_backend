package com.cetide.blog.model.dto;

import java.time.LocalDateTime;


/**
 * 短链接请求数据传输对象，用于创建或更新短链接
 */
public class ShortLinkDTO {
    private String description;
    private String domain;
    private String originUrl;
    private Boolean enabled;
    private Integer validDateType;
    private LocalDateTime validDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getValidDateType() {
        return validDateType;
    }

    public void setValidDateType(Integer validDateType) {
        this.validDateType = validDateType;
    }

    public LocalDateTime getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDateTime validDate) {
        this.validDate = validDate;
    }
}
