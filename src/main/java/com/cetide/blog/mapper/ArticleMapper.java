package com.cetide.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.blog.model.entity.Article;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT * FROM article WHERE deleted = 0 ORDER BY created_at DESC")
    Page<Article> findLatestArticles(Page<Article> page);

    @Select("SELECT * FROM cetide_blog.article WHERE id = #{id}")
    Article findById(@Param("id") Long id);

    @Select("SELECT * FROM cetide_blog.article " +
            "WHERE deleted = 0 " +
            "AND (title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR content LIKE CONCAT('%', #{keyword}, '%') " +
            "OR author_name LIKE CONCAT('%', #{keyword}, '%')) " +
            "ORDER BY created_at DESC ")
    Page<Article> selectArticles(Page<Article> page, @Param("keyword") String keyword);

    @Select("SELECT * FROM cetide_blog.article " +
            "WHERE deleted = 0 " +
            "ORDER BY created_at DESC ")
    Page<Article> selectArticle(Page<Article> page);

    Page<Article> selectArticlesByUserId(Page<Article> objectPage, String keyword, Long id);

    @Select("SELECT COUNT(*) FROM cetide_blog.article WHERE deleted = 0")
    long selectArticleCount();

    @Select("SELECT * FROM cetide_blog.article WHERE deleted = 0 ORDER BY created_at DESC LIMIT #{offset}, #{limit}")
    List<Article> selectArticleByPage(int offset, int i);

    void updateViewCount(@Param("articleId") Long articleId, @Param("count") Long count);
}