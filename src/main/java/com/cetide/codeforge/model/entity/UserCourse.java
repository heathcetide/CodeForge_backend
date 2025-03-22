package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName("user_course") // 对应数据库中的表名
public class UserCourse {

    @TableId
    private Long id;                // 中间表ID

    private Long userId;            // 用户ID

    private Long courseId;          // 课程ID

    private Date enrollDate;        // 用户报名日期

    private String progress;        // 学习进度（NOT_STARTED, IN_PROGRESS, COMPLETED）

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
