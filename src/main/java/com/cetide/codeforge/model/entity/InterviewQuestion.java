package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 面试题实体对象
 *
 * @author heathcetide
 */
@TableName("interview_questions")
public class InterviewQuestion {

    /**
     * 题目ID: 题目ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "题目ID: 题目ID")
    private Integer questionId;

    /**
     * 类别ID: 类别ID
     */
    @ApiModelProperty(value = "类别ID: 类别ID")
    private Integer categoryId;

    /**
     * 专题ID: 专题ID
     */
    @ApiModelProperty(value = "专题ID: 专题ID")
    private Integer topicId;

    /**
     * 题目内容: 题目内容
     */
    @ApiModelProperty(value = "题目内容: 题目内容")
    private String questionText;

    /**
     * 答案内容: 答案内容
     */
    @ApiModelProperty(value = "答案内容: 答案内容")
    private String answerText;

    /**
     * 创建人: 创建人
     */
    @ApiModelProperty(value = "创建人: 创建人")
    private Integer createBy;

    /**
     * 创建时间: 创建时间
     */
    @ApiModelProperty(value = "创建时间: 创建时间")
    private Date createTime;

    /**
     * 修改人: 修改人
     */
    @ApiModelProperty(value = "修改人: 修改人")
    private Integer updateBy;

    /**
     * 修改时间: 修改时间
     */
    @ApiModelProperty(value = "修改时间: 修改时间")
    private Date updateTime;

    public InterviewQuestion(){

    }

    public InterviewQuestion(Integer questionId, Integer categoryId, Integer topicId, String questionText, String answerText, Integer createBy, Date createTime, Integer updateBy, Date updateTime) {
        this.questionId = questionId;
        this.categoryId = categoryId;
        this.topicId = topicId;
        this.questionText = questionText;
        this.answerText = answerText;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
