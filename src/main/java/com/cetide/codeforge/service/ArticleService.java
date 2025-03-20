package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.ArticleCreateRequest;
import com.cetide.codeforge.model.dto.ArticleSearchParam;
import com.cetide.codeforge.model.entity.Article;
import com.cetide.codeforge.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService extends IService<Article> {
    boolean softDelete(Long id);
    
    List<Article> getTopViewedArticles(int limit);

    Page<Article> advancedSearch(ArticleSearchParam param, Page<Article> page);

    String createArticle(ArticleCreateRequest request, User author);

    Page<Article> getPublishedArticles(int page, int size);

    String uploadArticleImage(MultipartFile file) throws IOException;

    Page<Article> getArticles(Page<Article> objectPage, String keyword);

    Page<Article> getArticlesByUserId(Page<Article> objectPage, String keyword, Long id);

    long getTotalCount();

    List<Article> selectByPage(int offset, int i);

    void updateViewCount(Long articleId, Integer count);

    List<Long> getAllArticleIds();

}