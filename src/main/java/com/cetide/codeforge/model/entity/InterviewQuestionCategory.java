package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 面试题分类实体对象
 *
 * @author heathcetide
 */
@TableName("interview_question_categories")
public class InterviewQuestionCategory {

    /**
     * 类别ID: 类别ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "类别ID: 类别ID")
    private Integer categoryId;

    /**
     * 类别名称: 类别名称
     */
    @TableField("category_name")
    @ApiModelProperty(value = "类别名称: 类别名称")
    private String categoryName;

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

    /**
     * 是否删除: 是否删除
     */
    @ApiModelProperty(value = "是否删除: 是否删除")
    private Integer deleted;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
