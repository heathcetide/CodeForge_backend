package com.cetide.codeforge.model.dto;

public class InterviewQuestionSubmissionDTO {

    /**
     * 面试题ID
     */
    private Integer questionId;

    /**
     * 答案内容
     */
    private String answerText;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}