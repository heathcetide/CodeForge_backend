package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("course")
public class Course {

    /**
     * 课程ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程标题
     */
    @TableField("title")
    private String title;

    /**
     * 课程描述
     */
    @TableField("description")
    private String description;

    /**
     * 课程级别（例如 P2、P3、P4）
     */
    @TableField("level")
    private String level;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 课程难度（例如初级、中级、高级）
     */
    @TableField("difficulty")
    private String difficulty;

    /**
     * 封面图片地址
     */
    @TableField("cover_image")
    private String coverImage;

    /**
     * 预计学习时长（小时）
     */
    @TableField("estimated_hours")
    private Integer estimatedHours;

    /**
     * 星级评分
     */
    @TableField("star_rating")
    private Integer starRating;

    /**
     * 报名人数
     */
    @TableField("enroll_count")
    private Long enrollCount;

    /**
     * 是否推荐（true：推荐，false：不推荐）
     */
    @TableField("is_recommended")
    private Boolean isRecommended;

    /**
     * 课程关键字，用于搜索
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 课程状态（例如：'DRAFT', 'PUBLISHED', 'ARCHIVED'）
     */
    @TableField("status")
    private String status;

    /**
     * 是否免费（true：免费，false：付费）
     */
    @TableField("is_free")
    private Boolean isFree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Long getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(Long enrollCount) {
        this.enrollCount = enrollCount;
    }

    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean free) {
        isFree = free;
    }
}
