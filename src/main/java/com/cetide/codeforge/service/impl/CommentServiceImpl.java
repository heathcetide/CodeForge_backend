package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.mapper.ArticleMapper;
import com.cetide.codeforge.mapper.CommentMapper;
import com.cetide.codeforge.model.dto.CommentCreateRequest;
import com.cetide.codeforge.model.dto.CommentCreatedEvent;
import com.cetide.codeforge.model.entity.Article;
import com.cetide.codeforge.model.entity.Comment;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Page<Comment> getCommentsByArticle(Long articleId, Integer page, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public Comment createComment(CommentCreateRequest request, User user) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUserId(user.getId());
        Article article = articleMapper.findById(request.getArticleId());
        if (article == null) {
            throw new ResourceNotFoundException("文章不存在");
        }
        comment.setArticle(article);
        if (request.getParentId() != null) {
            Comment parent = commentMapper.findById(request.getParentId());
            if (parent == null) {
                throw new ResourceNotFoundException("父评论不存在");
            }
            comment.setParentId(parent.getId());
        }

        int insert = commentMapper.insert(comment);
        if (insert == 0) {
            throw new RuntimeException("评论创建失败");
        }
        // 保存评论

        // 发布评论创建事件
        applicationEventPublisher.publishEvent(new CommentCreatedEvent(
            comment.getId(),
            user.getUsername(),
            comment.getContent(),
            request.getArticleId()
        ));
        
        return comment;
    }

    @Override
    public Page<Comment> getArticleComments(Long articleId, Pageable pageable) {
        // 将 Pageable 转换为 MyBatis-Plus 的 Page 对象
        Page<Comment> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        // 使用转换后的 Page 对象调用 MyBatis-Plus 的分页查询方法
        return commentMapper.findByArticleIdAndParentIsNull(articleId, page);
    }

    @Override
    public Page<Comment> getCommentsByPage(Page<Comment> objectPage, Long id) {
        return commentMapper.selectCommentsByPage(objectPage, id);
    }

}
