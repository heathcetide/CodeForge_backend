package com.cetide.codeforge.model.dto;

import javax.validation.constraints.NotNull;
import com.cetide.codeforge.model.query.QueryGroup;
import io.swagger.annotations.ApiModelProperty;

/**
 * 随机问题查询对象
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class RandomQuestionDTO {

    /**
     * 用户ID:用户id，必填
     */
    @NotNull(groups = { QueryGroup.class }, message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID:用户id，必填")
    private Integer userId;

    public @NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer userId) {
        this.userId = userId;
    }
}
