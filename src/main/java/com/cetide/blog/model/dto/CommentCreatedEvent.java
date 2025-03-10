package com.cetide.blog.model.dto;

public class CommentCreatedEvent {
    private Long commentId;
    private String username;
    private String content;
    private Long articleId;

    public CommentCreatedEvent(Long commentId, String username, String content, Long articleId) {
        this.commentId = commentId;
        this.username = username;
        this.content = content;
        this.articleId = articleId;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
}
