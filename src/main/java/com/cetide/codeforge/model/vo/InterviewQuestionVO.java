package com.cetide.codeforge.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cetide.codeforge.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class InterviewQuestionVO {

    /**
     * 题目ID: 题目ID
     */
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
     * 创建时间: 创建时间
     */
    @ApiModelProperty(value = "创建时间: 创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
