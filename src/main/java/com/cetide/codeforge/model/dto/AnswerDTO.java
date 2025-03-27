package com.cetide.codeforge.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.cetide.codeforge.model.dto.group.CreateGroup;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户答题输入参数
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class AnswerDTO {

    /**
     * 用户ID，必填
     */
    @NotNull(groups = { CreateGroup.class }, message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID，必填")
    private Integer userId;

    /**
     * 题目ID，必填
     */
    @NotNull(groups = { CreateGroup.class }, message = "题目ID不能为空")
    @ApiModelProperty(value = "题目ID，必填")
    private Integer questionId;

    /**
     * 用户答案，必填
     */
    @NotBlank(groups = { CreateGroup.class }, message = "用户答案不能为空")
    @ApiModelProperty(value = "用户答案，必填")
    private String userAnswer;

    public @NotNull(groups = {CreateGroup.class}, message = "用户ID不能为空") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {CreateGroup.class}, message = "用户ID不能为空") Integer userId) {
        this.userId = userId;
    }

    public @NotNull(groups = {CreateGroup.class}, message = "题目ID不能为空") Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(@NotNull(groups = {CreateGroup.class}, message = "题目ID不能为空") Integer questionId) {
        this.questionId = questionId;
    }

    public @NotBlank(groups = {CreateGroup.class}, message = "用户答案不能为空") String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(@NotBlank(groups = {CreateGroup.class}, message = "用户答案不能为空") String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
