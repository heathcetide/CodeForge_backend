package com.cetide.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.blog.model.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT * FROM cetide_blog.comment WHERE article_id = #{articleId} AND parent_id IS NULL")
    Page<Comment> findByArticleIdAndParentIsNull(Long articleId, Page<Comment> page);

    @Select("SELECT * FROM cetide_blog.comment WHERE id = #{id}")
    Comment findById(Long id);

    Page<Comment> selectCommentsByPage(Page<Comment> objectPage, Long id);
}
