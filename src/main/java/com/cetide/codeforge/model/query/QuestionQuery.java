package com.cetide.codeforge.model.query;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

/**
 * 热度排行查询对象
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class QuestionQuery {

    /**
     * 题目数量限制
     */
    @Min(value = 1, groups = { QueryGroup.class }, message = "题目数量限制不能小于1")
    @ApiModelProperty(value = "题目数量限制")
    private int limit = 10;

    /**
     * 题目id
     */
    @NotNull(groups = { QueryGroup.class }, message = "题目id不能为空")
    @ApiModelProperty(value = "题目id")
    private Integer questionId;

    @Min(value = 1, groups = {QueryGroup.class}, message = "题目数量限制不能小于1")
    public int getLimit() {
        return limit;
    }

    public void setLimit(@Min(value = 1, groups = {QueryGroup.class}, message = "题目数量限制不能小于1") int limit) {
        this.limit = limit;
    }

    public @NotNull(groups = {QueryGroup.class}, message = "题目id不能为空") Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(@NotNull(groups = {QueryGroup.class}, message = "题目id不能为空") Integer questionId) {
        this.questionId = questionId;
    }
}
