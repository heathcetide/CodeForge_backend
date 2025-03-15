package com.cetide.blog.controller;

import com.cetide.blog.service.impl.LikeServiceImpl;
import com.cetide.blog.util.ApiResponse;
import com.google.protobuf.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@Api(tags = "用户点赞")
public class LikeController {

    @Autowired
    LikeServiceImpl likeService;

    @PutMapping("/{articleId}")
    @ApiOperation("点赞")
    public ApiResponse like(@PathVariable("articleId") Long articleId) throws ServiceException {

        likeService.like(articleId);
        return ApiResponse.success("点赞成功");
    }

    @PutMapping("/cancle/{articleId}")
    @ApiOperation("取消点赞")
    public ApiResponse disLike(@PathVariable("articleId") Long articleId){

        likeService.disLike(articleId);
        return ApiResponse.success("取消点赞");
    }
}
