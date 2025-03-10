package com.cetide.blog.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleCreateRequest {

    // 文章标题，不能为空
    private String title;

    // 文章内容，可以为空
    private String content;

    // 文章分类，可以为空
    private String category;

    private String tags;

    // 文章的简短摘要，可以为空
    private String excerpt;

    // 文章封面图片 URL，可以为空
    private String imageUrl;

    // 是否是草稿，0 已发布，1 草稿
    private Boolean isDraft;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

}
