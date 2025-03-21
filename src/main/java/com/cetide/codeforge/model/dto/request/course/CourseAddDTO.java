package com.cetide.codeforge.model.dto.request.course;

/**
 * 课程数据传输对象（DTO）
 * 用于在不同层之间传递课程的详细信息。
 */
public class CourseAddDTO {

    /**
     * 课程标题
     * 示例： "Advanced Java Programming"、"Introduction to SQL" 等
     */
    private String title;

    /**
     * 课程描述
     * 简要介绍课程内容，帮助用户快速了解课程核心内容
     */
    private String description;

    /**
     * 课程所属分类ID
     * 用于关联课程与具体的分类，方便课程的组织和搜索
     */
    private Long categoryId;

    /**
     * 课程级别
     * 表示课程的难度或阶段，例如： "P2"、"P3"、"P4" 等
     */
    private String level;

    /**
     * 课程难度
     * 表示课程的复杂程度，通常值有 "初级"、"中级"、"高级" 等
     */
    private String difficulty;

    /**
     * 课程封面图片
     * 存储课程封面图片的 URL 或文件路径，用于在页面上展示课程图片
     */
    private String coverImage;

    /**
     * 预计学习时长
     * 单位为小时，用于提示学生完成课程大概需要的时间
     */
    private Integer estimatedHours;

    /**
     * 是否推荐课程
     * 用于标识该课程是否被平台推荐或重点推荐，true 表示推荐
     */
    private Boolean isRecommended;

    /**
     * 课程关键字
     * 以逗号分隔的关键字集合，有助于搜索和优化课程匹配度
     */
    private String keywords;

    /**
     * 是否免费
     * true 表示该课程免费，false 表示需要付费
     */
    private Boolean isFree;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public Boolean getRecommended() {
        return isRecommended;
    }

    public void setRecommended(Boolean recommended) {
        isRecommended = recommended;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }
}