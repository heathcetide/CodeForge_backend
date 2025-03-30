package com.cetide.codeforge.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class InterviewQuestionListVO {

    /**
     * 题目ID: 题目ID
     */
    @ApiModelProperty(value = "题目ID: 题目ID")
    private Integer id;

    /**
     * 题目内容: 题目内容
     */
    @ApiModelProperty(value = "题目内容: 题目内容")
    private String questionText;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
