package com.cetide.blog.service;

import java.util.Set;

public interface ArticleSearchService {

    void addArticleDocumentToIndex(String keyword, String docId);

    Set<String> searchDocuments(Set<String> keywords);
}
