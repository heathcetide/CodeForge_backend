package com.cetide.codeforge.model.vo;

public class CourseVO {

    /**
     * id
     */
    private String id;
    /**
     * 课程标题
     */
    private final String title;

    /**
     * 课程描述
     */
    private final String description;

    /**
     * 课程级别（例如 P2、P3、P4）
     */
    private final String level;

    /**
     * 课程难度（例如初级、中级、高级）
     */
    private final String difficulty;

    /**
     * 封面图片地址
     */
    private final String coverImage;

    /**
     * 预计学习时长（小时）
     */
    private final Integer estimatedHours;

    /**
     * 星级评分
     */
    private final Integer starRating;

    /**
     * 报名人数
     */
    private final Long enrollCount;

    /**
     * 是否推荐（true：推荐，false：不推荐）
     */
    private final Boolean recommended;

    /**
     * 课程状态（例如：'DRAFT', 'PUBLISHED', 'ARCHIVED'）
     */
    private final String status;

    /**
     * 是否免费（true：免费，false：付费）
     */
    private final Boolean isFree;

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
}