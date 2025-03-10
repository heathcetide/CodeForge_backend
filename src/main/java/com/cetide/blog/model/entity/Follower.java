package com.cetide.blog.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

@TableName("followers")
public class Follower extends BaseEntity{

    private Long followerId;  // 粉丝ID

    private Long followedUserId;  // 被关注者ID

    private LocalDateTime createdAt;  // 关注时间

    private LocalDateTime firstFollowedAt;  // 首次关注时间

    private String status;  // 关注状态（following, unfollowed, pending）

    private Boolean isVerified;  // 是否验证过的关注关系

    private String tags;  // 关注标签，使用 JSON 格式

    private Integer followWeight;  // 关注关系的权重

    private LocalDateTime unfollowedAt;  // 取消关注时间

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(Long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public LocalDateTime getFirstFollowedAt() {
        return firstFollowedAt;
    }

    public void setFirstFollowedAt(LocalDateTime firstFollowedAt) {
        this.firstFollowedAt = firstFollowedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getFollowWeight() {
        return followWeight;
    }

    public void setFollowWeight(Integer followWeight) {
        this.followWeight = followWeight;
    }

    public LocalDateTime getUnfollowedAt() {
        return unfollowedAt;
    }

    public void setUnfollowedAt(LocalDateTime unfollowedAt) {
        this.unfollowedAt = unfollowedAt;
    }
}