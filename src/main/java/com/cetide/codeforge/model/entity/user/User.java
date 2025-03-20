package com.cetide.codeforge.model.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cetide.codeforge.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squareup.okhttp.HttpUrl;

import java.io.Serializable;
import java.util.Date;

/**
 * User entity class representing the user information for the blog system.
 * Includes fields for user account details, preferences, and relationships.
 */
@TableName("users")
public class User extends BaseEntity implements Serializable {

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "encryption_method")
    private String encryptionMethod;

    @TableField(value = "email")
    private String email;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "role")
    private String role;

    @TableField(value = "permissions")
    private String permissions;

    @TableField(value = "birthday")
    private Date birthday;

    @TableField(value = "gender")
    private Integer gender;

    @TableField(value = "address")
    private String address;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "points")
    private Long points;

    @TableField(value = "login_count")
    private Long loginCount;

    @TableField(value = "article_count")
    private Long articleCount;

    @TableField(value = "activity_count")
    private Long activityCount;

    @TableField("password_salt")
    private String passwordSalt;

    @TableField("password_updated_at")
    private Date passwordUpdatedAt;

    @TableField("login_attempts")
    private int loginAttempts;

    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    @TableField("lock_time")
    private Date lockTime;

    @TableField("account_non_locked")
    private boolean accountNonLocked = true;

    @TableField(value = "bio")
    private String bio;

    @TableField(exist = false)
    @JsonIgnore
    private static final long serialVersionUID = 1L;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
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

    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    public void setEncryptionMethod(String encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Date getPasswordUpdatedAt() {
        return passwordUpdatedAt;
    }

    public void setPasswordUpdatedAt(Date passwordUpdatedAt) {
        this.passwordUpdatedAt = passwordUpdatedAt;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
