package com.cetide.codeforge.model.dto;

import com.cetide.codeforge.model.entity.Article;

// 用于存储文章和其相应的权重
public class ArticleWithWeight {
    private Article article;
    private double weight;

    public ArticleWithWeight(Article article, double weight) {
        this.article = article;
        this.weight = weight;
    }

    public Article getArticle() {
        return article;
    }

    public double getWeight() {
        return weight;
    }
}