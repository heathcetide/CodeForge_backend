package com.cetide.codeforge.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cetide.codeforge.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class UserVO {

    private String username;

    private String email;

    private String phone;

    private String address;

    private Long points;

    private Long articleCount;

    private Long activityCount;

    private String passwordSalt;

    private String avatarUrl;

    private Integer gender;

    private String bio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date lastLoginTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Long articleCount) {
        this.articleCount = articleCount;
    }

    public Long getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Long activityCount) {
        this.activityCount = activityCount;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserVO toUserVO(User user){
        UserVO userVO = new UserVO();
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setAvatarUrl(user.getAvatar());
        userVO.setBirthday(user.getBirthday());
        userVO.setGender(user.getGender());
        userVO.setLastLoginTime(user.getLastLoginTime());
        userVO.setBio(user.getBio());
        userVO.setAddress(user.getAddress());
        userVO.setPoints(user.getPoints());
        userVO.setArticleCount(user.getArticleCount());
        userVO.setActivityCount(user.getActivityCount());
        userVO.setPasswordSalt(user.getPasswordSalt());
        userVO.setCreatedAt(user.getCreatedAt());
        return userVO;
    }
}
