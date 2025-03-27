package com.cetide.codeforge.model.vo;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 题目详情视图对象
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class QuestionDetailVO {

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID")
    private Integer questionId;

    /**
     * 类别ID
     */
    @ApiModelProperty(value = "类别ID")
    private Integer categoryId;

    /**
     * 专题ID
     */
    @ApiModelProperty(value = "专题ID")
    private Integer topicId;

    /**
     * 题目内容
     */
    @ApiModelProperty(value = "题目内容")
    private String questionText;

    /**
     * 答案内容
     */
    @ApiModelProperty(value = "答案内容")
    private String answerText;

    /**
     * 热度分数
     */
    @ApiModelProperty(value = "热度分数")
    private Integer popularityScore;

    /**
     * 最后被测试的时间
     */
    @ApiModelProperty(value = "最后被测试的时间")
    private Date lastTested;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Integer popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Date getLastTested() {
        return lastTested;
    }

    public void setLastTested(Date lastTested) {
        this.lastTested = lastTested;
    }
}
