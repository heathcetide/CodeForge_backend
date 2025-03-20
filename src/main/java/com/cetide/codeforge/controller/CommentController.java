package com.cetide.codeforge.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.model.dto.AddCommentDTO;
import com.cetide.codeforge.model.entity.Article;
import com.cetide.codeforge.model.entity.Comment;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.CommentVO;
import com.cetide.codeforge.service.ArticleService;
import com.cetide.codeforge.service.CommentService;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.util.common.RedisUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        Page<Comment> userPage = commentService.getCommentsByPage(new Page<>(1, 10), 4L);
        Page<CommentVO> commentVOPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : userPage.getRecords()) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            User userById = userService.getUserById(comment.getUserId());
            commentVO.setUsername(userById.getUsername());
            commentVO.setAvatarUrl(userById.getAvatar());
            commentVOList.add(commentVO);
        }
        commentVOPage.setRecords(commentVOList);
        redisUtils.set("Comment_List", commentVOPage);
    }

    /**
     * 分页查询评论
     *
     * @param page 页码，从1开始，默认值为1
     * @param size 每页记录数，默认值为10
     * @param id   文章id
     * @return 分页用户数据
     */
    @GetMapping("/get/page/{id}")
    @Operation(summary = "分页查询评论")
    public ApiResponse<Page<CommentVO>> getAllUsers(
            @PathVariable("id") Long id,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page) {

        Page<Comment> userPage = commentService.getCommentsByPage(new Page<>(page, size), id);
        Page<CommentVO> commentVOPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : userPage.getRecords()) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            User userById = userService.getUserById(comment.getUserId());
            commentVO.setUsername(userById.getUsername());
            commentVO.setAvatarUrl(userById.getAvatar());
            commentVO.setParentId(String.valueOf(comment.getParentId()));
            commentVO.setArticleId(String.valueOf(comment.getArticleId()));
            commentVOList.add(commentVO);
        }
        commentVOPage.setRecords(commentVOList);
        return ApiResponse.success(commentVOPage);
    }


    @PostMapping(value = "/add")
    public ApiResponse<CommentVO> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        User user = AuthContext.getCurrentUser();
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentDTO, comment);
        comment.setUserId(user.getId());
        comment.setDeleted(0);
        boolean save = commentService.save(comment);
        Article article = articleService.getById(comment.getArticleId());
        article.setCommentCount(article.getCommentCount() + 1);
        articleService.updateById(article);
        if (save) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setUsername(user.getUsername());
            commentVO.setAvatarUrl(user.getAvatar());
            return ApiResponse.success(commentVO);
        }else{
            return ApiResponse.error(400, "评论失败");
        }
    }
}
