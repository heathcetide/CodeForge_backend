package com.cetide.codeforge.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cetide.codeforge.model.entity.Article;
import com.cetide.codeforge.model.entity.BaseEntity;

@TableName("comment")
public class CommentVO extends BaseEntity {

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 用户ID
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID
     */
    private String parentId;

    @TableField(exist = false)
    private Article article;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
