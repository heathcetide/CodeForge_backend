package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.CommentCreateRequest;
import com.cetide.codeforge.model.entity.Comment;
import com.cetide.codeforge.model.entity.user.User;
import org.springframework.data.domain.Pageable;

public interface CommentService extends IService<Comment> {

    Page<Comment> getCommentsByArticle(Long articleId, Integer page, Integer size);

    Comment createComment(CommentCreateRequest request, User user);

    Page<Comment> getArticleComments(Long articleId, Pageable pageable);

    Page<Comment> getCommentsByPage(Page<Comment> objectPage, Long id);
}
