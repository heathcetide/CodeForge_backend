package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * 面试题实体对象
 *
 * @author heathcetide
 */
@TableName("interview_question")
public class InterviewQuestion {

    /**
     * 题目ID: 题目ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "题目ID: 题目ID")
    private Integer id;

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
    private Long createBy;

    /**
     * 创建时间: 创建时间
     */
    @ApiModelProperty(value = "创建时间: 创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人: 修改人
     */
    @ApiModelProperty(value = "修改人: 修改人")
    private Long updateBy;

    /**
     * 修改时间: 修改时间
     */
    @ApiModelProperty(value = "修改时间: 修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public InterviewQuestion(){

    }

    public InterviewQuestion(Integer id, Integer topicId, String questionText, String answerText, Long createBy, Date createTime, Long updateBy, Date updateTime) {
        this.id = id;
        this.topicId = topicId;
        this.questionText = questionText;
        this.answerText = answerText;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
