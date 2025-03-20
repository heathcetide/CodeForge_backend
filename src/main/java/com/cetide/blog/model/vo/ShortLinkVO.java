package com.cetide.blog.model.vo;

import com.cetide.blog.model.entity.ShortLink;

import java.time.LocalDateTime;

/**
 * 短链接响应视图对象，用于向前端返回数据
 */
public class ShortLinkVO {
    private Long id;
    private String description;
    private String domain;
    private String shortUrl;
    private String fullShortUrl;
    private Long clickNum;
    private String originUrl;
    private Boolean enabled;
    private Integer validDateType;
    private LocalDateTime validDate;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    /** 将实体对象转换为视图对象 */
    public static ShortLinkVO fromEntity(ShortLink shortLink) {
        if (shortLink == null) {
            return null;
        }
        ShortLinkVO vo = new ShortLinkVO();
        vo.setId(shortLink.getId());
        vo.setDescription(shortLink.getDescription());
        vo.setDomain(shortLink.getDomain());
        vo.setShortUrl(shortLink.getShortUrl());
        vo.setFullShortUrl(shortLink.getFullShortUrl());
        vo.setClickNum(shortLink.getClickNum());
        vo.setOriginUrl(shortLink.getOriginUrl());
        vo.setEnabled(shortLink.getEnabled());
        vo.setValidDateType(shortLink.getValidDateType());
        vo.setValidDate(shortLink.getValidDate());
        vo.setUpdatedAt(shortLink.getUpdatedAt());
        vo.setCreatedAt(shortLink.getCreatedAt());
        return vo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getFullShortUrl() {
        return fullShortUrl;
    }

    public void setFullShortUrl(String fullShortUrl) {
        this.fullShortUrl = fullShortUrl;
    }

    public Long getClickNum() {
        return clickNum;
    }

    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
