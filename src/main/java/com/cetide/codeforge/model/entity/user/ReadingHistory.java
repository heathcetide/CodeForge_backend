package com.cetide.codeforge.model.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cetide.codeforge.model.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName("reading_history")
public class ReadingHistory extends BaseEntity {

    private Long userId;  // 用户ID

    private Long articleId;  // 文章ID

    private LocalDateTime readAt;  // 阅读时间

    private Integer readingTime;  // 阅读时长（单位：秒）

    private String status;  // 阅读状态 (read, unread, in_progress)

    private String device;  // 阅读设备

    private String browser;  // 浏览器信息

    private Long articleCategoryId;  // 文章分类ID

    private String tags;  // 文章标签（JSON格式）

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public Integer getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Integer readingTime) {
        this.readingTime = readingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}