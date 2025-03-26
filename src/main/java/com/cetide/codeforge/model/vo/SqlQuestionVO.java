package com.cetide.codeforge.model.vo;

public class SqlQuestionVO {

    /**
     * id
     */
    private Long id;

    /**
     * exam_id
     */
    private Long examId;

    /**
     * title
     */
    private String title;

    /**
     * description
     */
    private String description;

    /**
     * score
     */
    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
