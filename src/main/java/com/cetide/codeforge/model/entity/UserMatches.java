package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户匹配PK实体
 *
 * @author heathcetide
 */
@TableName("user_matches")
public class UserMatches {

    /**
     * 比赛ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "比赛ID")
    private Integer matchId;

    /**
     * 用户1 ID
     */
    @ApiModelProperty(value = "用户1 ID")
    private Integer userId1;

    /**
     * 用户2 ID
     */
    @ApiModelProperty(value = "用户2 ID")
    private Integer userId2;

    /**
     * 比赛开始时间
     */
    @ApiModelProperty(value = "比赛开始时间")
    private Date matchStartTime;

    /**
     * 比赛结束时间
     */
    @ApiModelProperty(value = "比赛结束时间")
    private Date matchEndTime;

    /**
     * 胜者用户ID
     */
    @ApiModelProperty(value = "胜者用户ID")
    private Integer winnerUserId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Integer updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public Date getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Date matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Date getMatchEndTime() {
        return matchEndTime;
    }

    public void setMatchEndTime(Date matchEndTime) {
        this.matchEndTime = matchEndTime;
    }

    public Integer getWinnerUserId() {
        return winnerUserId;
    }

    public void setWinnerUserId(Integer winnerUserId) {
        this.winnerUserId = winnerUserId;
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
