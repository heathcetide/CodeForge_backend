package com.cetide.codeforge.model.query;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import io.swagger.annotations.ApiModelProperty;

/**
 * 面试题查询对象
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class InterviewQuestionQuery {

    /**
     * 类别ID: 面试题所属的类别ID，必填
     */
    @NotNull(groups = { QueryGroup.class, Default.class }, message = "类别ID不能为空")
    @ApiModelProperty(value = "类别ID: 面试题所属的类别ID，必填")
    private Integer categoryId;

    /**
     * 专题ID: 面试题所属的专题ID，必填
     */
    @NotNull(groups = { QueryGroup.class, Default.class }, message = "专题ID不能为空")
    @ApiModelProperty(value = "专题ID: 面试题所属的专题ID，必填")
    private Integer topicId;

    /**
     * 分页索引: 分页查询的索引
     */
    @NotNull(groups = { QueryGroup.class, Default.class }, message = "分页索引不能为空")
    @ApiModelProperty(value = "分页索引: 分页查询的索引")
    private Integer pageIndex;

    /**
     * 分页大小: 分页查询的大小
     */
    @NotNull(groups = { QueryGroup.class, Default.class }, message = "分页大小不能为空")
    @ApiModelProperty(value = "分页大小: 分页查询的大小")
    private Integer pageSize;

    public @NotNull(groups = {QueryGroup.class, Default.class}, message = "类别ID不能为空") Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NotNull(groups = {QueryGroup.class, Default.class}, message = "类别ID不能为空") Integer categoryId) {
        this.categoryId = categoryId;
    }

    public @NotNull(groups = {QueryGroup.class, Default.class}, message = "专题ID不能为空") Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(@NotNull(groups = {QueryGroup.class, Default.class}, message = "专题ID不能为空") Integer topicId) {
        this.topicId = topicId;
    }

    public @NotNull(groups = {QueryGroup.class, Default.class}, message = "分页索引不能为空") Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(@NotNull(groups = {QueryGroup.class, Default.class}, message = "分页索引不能为空") Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public @NotNull(groups = {QueryGroup.class, Default.class}, message = "分页大小不能为空") Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(@NotNull(groups = {QueryGroup.class, Default.class}, message = "分页大小不能为空") Integer pageSize) {
        this.pageSize = pageSize;
    }
}
