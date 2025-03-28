package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 面试题专题实体对象
 *
 * @author heathcetide
 */
@TableName("interview_question_topic")
public class InterviewQuestionTopic {

    /**
     * 专题ID: 专题ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "专题ID: 专题ID")
    private Long id;

    /**
     * 专题名称: 专题名称
     */
    @ApiModelProperty(value = "专题名称: 专题名称")
    private String topicName;

    /**
     * 创建人: 创建人
     */
    @ApiModelProperty(value = "创建人: 创建人")
    private Integer createBy;

    /**
     * 创建时间: 创建时间
     */
    @ApiModelProperty(value = "创建时间: 创建时间")
    private Date createTime;

    /**
     * 修改人: 修改人
     */
    @ApiModelProperty(value = "修改人: 修改人")
    private Integer updateBy;

    /**
     * 修改时间: 修改时间
     */
    @ApiModelProperty(value = "修改时间: 修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "专题图片")
    private String coverImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
