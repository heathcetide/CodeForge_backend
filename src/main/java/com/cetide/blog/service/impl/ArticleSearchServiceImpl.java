package com.cetide.blog.service.impl;
import com.cetide.blog.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 将文档关键词添加到倒排索引中
    public void addArticleDocumentToIndex(String keyword, String docId) {
        redisTemplate.opsForSet().add("index:" + keyword, docId);
    }

    // 查询包含某些关键词的文档
    public Set<String> searchDocuments(Set<String> keywords) {
        Set<String> result = null;

        for (String keyword : keywords) {
            Set<String> docsForKeyword = redisTemplate.opsForSet().members("index:" + keyword);
            if (docsForKeyword != null) {
                if (result == null) {
                    result = docsForKeyword;
                } else {
                    result.retainAll(docsForKeyword);  // 求交集
                }
            }
        }
        return result;
    }
}
