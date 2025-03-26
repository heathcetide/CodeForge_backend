package com.cetide.codeforge.model.dto;

public class ExamDTO {

    private Long chapterId;

    private String examType;

    private Integer passScore;

    private String description;

    private Integer maxAttempts;

    private String questionBank;

    private Integer shuffleQuestions;

    private Integer showAnswers;

    private String proctoringSettings;

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

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(String questionBank) {
        this.questionBank = questionBank;
    }

    public Integer getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Integer shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public Integer getShowAnswers() {
        return showAnswers;
    }

    public void setShowAnswers(Integer showAnswers) {
        this.showAnswers = showAnswers;
    }

    public String getProctoringSettings() {
        return proctoringSettings;
    }

    public void setProctoringSettings(String proctoringSettings) {
        this.proctoringSettings = proctoringSettings;
    }
}