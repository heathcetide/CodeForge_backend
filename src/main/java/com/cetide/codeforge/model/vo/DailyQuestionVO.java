package com.cetide.codeforge.model.vo;

public class DailyQuestionVO {

    private QuestionVO question;

    private String questionImg;

    public QuestionVO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionVO question) {
        this.question = question;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }
}
