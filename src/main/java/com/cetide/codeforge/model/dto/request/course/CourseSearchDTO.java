package com.cetide.codeforge.model.dto.request.course;


/**
 * 课程搜索数据传输对象 (DTO)
 * 用于封装查询课程时的搜索条件，支持对课程标题、分类、级别、难度等条件进行模糊或精确匹配。
 */
public class CourseSearchDTO {

    /**
     * 课程标题关键字
     * 用于模糊匹配课程的标题，例如用户输入 "Java" 后可匹配包含 "Java" 的课程标题。
     */
    private String title;

    /**
     * 课程所属分类ID
     * 精确匹配某个分类下的课程。如果该值为 null，则不作为过滤条件。
     */
    private Long categoryId;

    /**
     * 课程级别
     * 表示课程的阶段或难度层次，例如 "P2", "P3", "P4" 等。
     */
    private String level;

    /**
     * 课程难度
     * 用于描述课程的复杂程度，例如 "初级"、"中级"、"高级" 等。
     */
    private String difficulty;

    /**
     * 是否推荐课程
     * true 表示只查询推荐的课程，false 表示只查询非推荐课程，
     * 如果为 null 则不作为过滤条件。
     */
    private Boolean isRecommended;

    /**
     * 是否免费课程
     * true 表示查询免费课程，false 表示查询付费课程，
     * 如果为 null 则不作为过滤条件。
     */
    private Boolean isFree;

    /**
     * 课程关键字
     * 用于模糊搜索的其他关键字，可提高搜索匹配度，多个关键字可用逗号分隔。
     */
    private String keywords;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Boolean getRecommended() {
        return isRecommended;
    }

    public void setRecommended(Boolean recommended) {
        isRecommended = recommended;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}