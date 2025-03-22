package com.cetide.codeforge.model.vo;

public class CourseVO {

    /**
     * id
     */
    private String id;
    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程级别（例如 P2、P3、P4）
     */
    private String level;

    /**
     * 课程难度（例如初级、中级、高级）
     */
    private String difficulty;

    /**
     * 封面图片地址
     */
    private String coverImage;

    /**
     * 预计学习时长（小时）
     */
    private Integer estimatedHours;

    /**
     * 星级评分
     */
    private Integer starRating;

    /**
     * 报名人数
     */
    private Long enrollCount;

    /**
     * 是否推荐（true：推荐，false：不推荐）
     */
    private Boolean recommended;

    /**
     * 课程状态（例如：'DRAFT', 'PUBLISHED', 'ARCHIVED'）
     */
    private String status;

    /**
     * 是否免费（true：免费，false：付费）
     */
    private Boolean isFree;

    /**
     * 是否学习
     */
    private Boolean isStudy;

    private CourseVO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.level = builder.level;
        this.difficulty = builder.difficulty;
        this.coverImage = builder.coverImage;
        this.estimatedHours = builder.estimatedHours;
        this.starRating = builder.starRating;
        this.enrollCount = builder.enrollCount;
        this.recommended = builder.recommended;
        this.status = builder.status;
        this.isFree = builder.isFree;
        this.isStudy = builder.isStudy;
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private String level;
        private String difficulty;
        private String coverImage;
        private Integer estimatedHours;
        private Integer starRating;
        private Long enrollCount;
        private Boolean recommended;
        private String status;
        private Boolean isFree;
        private Boolean isStudy;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setLevel(String level) {
            this.level = level;
            return this;
        }

        public Builder setDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setCoverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public Builder setEstimatedHours(Integer estimatedHours) {
            this.estimatedHours = estimatedHours;
            return this;
        }

        public Builder setStarRating(Integer starRating) {
            this.starRating = starRating;
            return this;
        }

        public Builder setEnrollCount(Long enrollCount) {
            this.enrollCount = enrollCount;
            return this;
        }

        public Builder setRecommended(Boolean recommended) {
            this.recommended = recommended;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setFree(Boolean isFree) {
            this.isFree = isFree;
            return this;
        }

        public Builder setStudy(Boolean study) {
            this.isStudy = study;
            return this;
        }

        public CourseVO build() {
            return new CourseVO(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public Long getEnrollCount() {
        return enrollCount;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getFree() {
        return isFree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getStudy() {
        return isStudy;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public void setEnrollCount(Long enrollCount) {
        this.enrollCount = enrollCount;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public void setStudy(Boolean study) {
        isStudy = study;
    }
}