package com.cetide.codeforge.model.query;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户匹配PK查询参数
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class UserMatchesQuery {

    /**
     * 比赛ID
     */
    @NotNull(groups = { QueryGroup.class }, message = "比赛ID不能为空")
    @ApiModelProperty(value = "比赛ID")
    private Integer matchId;

    /**
     * 用户ID
     */
    @NotNull(groups = { QueryGroup.class }, message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    /**
     * 分页索引
     */
    @ApiModelProperty(value = "分页索引")
    private Integer pageIndex;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    public @NotNull(groups = {QueryGroup.class}, message = "比赛ID不能为空") Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(@NotNull(groups = {QueryGroup.class}, message = "比赛ID不能为空") Integer matchId) {
        this.matchId = matchId;
    }

    public @NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(groups = {QueryGroup.class}, message = "用户ID不能为空") Integer userId) {
        this.userId = userId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
