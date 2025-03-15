package com.cetide.blog.service;

import com.google.protobuf.ServiceException;

public interface LikeService {

    /**
     * 用户点赞
     */
    void like(Long articleId) throws ServiceException;

    /**
     * 用户取消点赞
     * @param articleId
     */
    void disLike(Long articleId);
}
