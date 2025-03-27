package com.cetide.codeforge.model.dto;

import javax.validation.constraints.NotNull;

import com.cetide.codeforge.model.dto.group.CreateGroup;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户匹配PK发起参数
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class UserMatchesDTO {

    /**
     * 用户1 ID
     */
    @NotNull(groups = { CreateGroup.class }, message = "用户1 ID不能为空")
    @ApiModelProperty(value = "用户1 ID")
    private Integer userId1;

    /**
     * 用户2 ID
     */
    @NotNull(groups = { CreateGroup.class }, message = "用户2 ID不能为空")
    @ApiModelProperty(value = "用户2 ID")
    private Integer userId2;

    public @NotNull(groups = {CreateGroup.class}, message = "用户1 ID不能为空") Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(@NotNull(groups = {CreateGroup.class}, message = "用户1 ID不能为空") Integer userId1) {
        this.userId1 = userId1;
    }

    public @NotNull(groups = {CreateGroup.class}, message = "用户2 ID不能为空") Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(@NotNull(groups = {CreateGroup.class}, message = "用户2 ID不能为空") Integer userId2) {
        this.userId2 = userId2;
    }
}
