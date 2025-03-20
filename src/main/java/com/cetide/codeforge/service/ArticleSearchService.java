package com.cetide.codeforge.service;

import java.util.Set;

public interface ArticleSearchService {

    void addArticleDocumentToIndex(String keyword, String docId);

    Set<String> searchDocuments(Set<String> keywords);
}
