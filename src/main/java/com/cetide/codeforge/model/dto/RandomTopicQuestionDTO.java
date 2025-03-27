package com.cetide.codeforge.model.dto;

import javax.validation.constraints.NotNull;
import com.cetide.codeforge.model.query.QueryGroup;
import io.swagger.annotations.ApiModelProperty;

/**
 * 专题随机问题查询对象
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class RandomTopicQuestionDTO {

    /**
     * 用户ID:用户id，必填
     */
    @NotNull(groups = { QueryGroup.class }, message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID:用户id，必填")
    private Integer userId;

    /**
     * 专题ID:专题id，必填
     */
    @NotNull(groups = { QueryGroup.class }, message = "专题ID不能为空")
    @ApiModelProperty(value = "专题ID:专题id，必填")
    private Integer topicId;

    public @NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer userId) {
        this.userId = userId;
    }

    public @NotNull(groups = {QueryGroup.class}, message = "专题ID不能为空") Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(@NotNull(groups = {QueryGroup.class}, message = "专题ID不能为空") Integer topicId) {
        this.topicId = topicId;
    }
}
