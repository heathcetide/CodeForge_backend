package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("chapter")
public class Chapter {

    /**
     * 章节ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程ID
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 父章节ID，0表示根节点
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 章节标题
     */
    @TableField("title")
    private String title;

    /**
     * 排序序号
     */
    @TableField("order_index")
    private Integer orderIndex;

    /**
     * 是否是叶子节点（最小节）
     */
    @TableField("is_leaf")
    private Boolean isLeaf;

    /**
     * 内容类型（例如：VIDEO、ARTICLE、QUIZ）
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 预计学习时长（分钟）
     */
    @TableField("duration_minutes")
    private Integer durationMinutes;

    /**
     * 附件资源地址
     */
    @TableField("attachment_url")
    private String attachmentUrl;

    /**
     * 章节内容，支持Markdown语法
     */
    @TableField("markdown_content")
    private String markdownContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent;
    }
}
