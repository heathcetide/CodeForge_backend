package com.cetide.codeforge.model.entity.exams;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
@TableName("exam")
public class Exam {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long chapterId;

    private String examType; // 'CHAPTER_TEST', 'FINAL_EXAM'

    private Integer passScore;

    private String description;

    private Integer maxAttempts;

    private LocalDateTime availableFrom;

    private LocalDateTime availableUntil;

    private String questionBank; // JSON string

    private Boolean shuffleQuestions;

    private Boolean showAnswers;

    private String proctoringSettings; // JSON string

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

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDateTime getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalDateTime availableUntil) {
        this.availableUntil = availableUntil;
    }

    public String getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(String questionBank) {
        this.questionBank = questionBank;
    }

    public Boolean getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public Boolean getShowAnswers() {
        return showAnswers;
    }

    public void setShowAnswers(Boolean showAnswers) {
        this.showAnswers = showAnswers;
    }

    public String getProctoringSettings() {
        return proctoringSettings;
    }

    public void setProctoringSettings(String proctoringSettings) {
        this.proctoringSettings = proctoringSettings;
    }
}
