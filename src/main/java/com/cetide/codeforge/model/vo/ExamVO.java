package com.cetide.codeforge.model.vo;

import java.util.List;

public class ExamVO {

    private Long id;

    private Long chapterId;

    private String examType;

    private Integer passScore;

    private String description;

    private List<SqlQuestionVO> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SqlQuestionVO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SqlQuestionVO> questions) {
        this.questions = questions;
    }
}