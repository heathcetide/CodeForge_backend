package com.cetide.codeforge.model.dto;

/**
 * 用户点赞
 */
public class LikeDto {
    private Long userId;
    private Long articleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }


}
