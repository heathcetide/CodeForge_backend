package com.cetide.codeforge.model.query;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取答题反馈输入参数
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class AnswerQuery {

    /**
     * 用户ID，必填
     */
    @NotNull(groups = { QueryGroup.class }, message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID，必填")
    private Integer userId;

    /**
     * 题目ID，必填
     */
    @NotNull(groups = { QueryGroup.class }, message = "题目ID不能为空")
    @ApiModelProperty(value = "题目ID，必填")
    private Integer questionId;

    public @NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer userId) {
        this.userId = userId;
    }

    public @NotNull(groups = {QueryGroup.class}, message = "题目ID不能为空") Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(@NotNull(groups = {QueryGroup.class}, message = "题目ID不能为空") Integer questionId) {
        this.questionId = questionId;
    }
}
