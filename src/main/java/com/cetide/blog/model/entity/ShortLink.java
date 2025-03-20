package com.cetide.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短链接实体类，对应数据库表 short_link
 */
@TableName("short_link")
public class ShortLink implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)  // 主键，使用雪花算法生成ID
    private Long id;

    private Long gid;  // 全局ID或分组ID

    private String description;

    private String domain;

    @TableField("short_url")
    private String shortUrl;

    @TableField("full_short_url")
    private String fullShortUrl;

    @TableField("click_num")
    private Long clickNum;

    @TableField("origin_url")
    private String originUrl;

    private Boolean enabled;

    @TableField("valid_date_type")
    private Integer validDateType;

    @TableField("valid_date")
    private LocalDateTime validDate;

    // 逻辑删除标识（0 未删除，1 已删除）
    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("created_at")
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
