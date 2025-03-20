package com.cetide.codeforge.model.dto;

public class CommentCreateRequest {
    private String content;      // Comment content
    private Long articleId;      // ID of the article
    private Long parentId;       // ID of the parent comment (nullable)

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
