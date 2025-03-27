package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 题目热度实体类
 *
 * @author heathcetide
 */
@TableName("question_popularity")
public class QuestionPopularity {

    /**
     * 热度ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "热度ID")
    private Integer popularityId;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID")
    private Integer questionId;

    /**
     * 热度分数
     */
    @ApiModelProperty(value = "热度分数")
    private Integer popularityScore;

    /**
     * 最后被测试的时间
     */
    @ApiModelProperty(value = "最后被测试的时间")
    private Date lastTested;

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

    public Integer getPopularityId() {
        return popularityId;
    }

    public void setPopularityId(Integer popularityId) {
        this.popularityId = popularityId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Integer popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Date getLastTested() {
        return lastTested;
    }

    public void setLastTested(Date lastTested) {
        this.lastTested = lastTested;
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
