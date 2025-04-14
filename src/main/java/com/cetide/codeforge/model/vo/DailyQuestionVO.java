package com.cetide.codeforge.model.vo;


import com.cetide.codeforge.model.entity.question.Question;

public class DailyQuestionVO {
    private Question question;

    private String questionImg;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }
}
