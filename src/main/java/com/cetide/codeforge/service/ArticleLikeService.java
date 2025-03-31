package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.ArticleLike;
import com.google.protobuf.ServiceException;

public interface ArticleLikeService extends IService<ArticleLike> {

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
