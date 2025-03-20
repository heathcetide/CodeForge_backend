package com.cetide.codeforge.model.dto;

import java.time.LocalDate;

public class ArticleSearchParam {
    private String keyword;
    private Long categoryId;
    private String tag;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public ArticleSearchParam setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ArticleSearchParam setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public ArticleSearchParam setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ArticleSearchParam setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ArticleSearchParam setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
