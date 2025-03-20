package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("article_activity_record")
public class ArticleActivityRecord extends BaseEntity{

    private int year;

    private int month;

    private int day;

    private int composeCount;

    private Long userId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getComposeCount() {
        return composeCount;
    }

    public void setComposeCount(int composeCount) {
        this.composeCount = composeCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}