package com.cetide.codeforge.model.vo;

import com.cetide.codeforge.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class UserVO {

    private String username;

    private String email;

    private String phone;

    private String avatarUrl;

    private Integer gender;

    private String bio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date lastLoginTime;

    private String theme;

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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
        return userVO;
    }
}
