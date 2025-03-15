package com.cetide.blog.service.impl;


import com.cetide.blog.common.auth.AuthContext;
import com.cetide.blog.common.constants.Constants;
import com.cetide.blog.exception.NoPermissionException;
import com.cetide.blog.mapper.LikeMapper;
import com.cetide.blog.model.entity.Like;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {
    
    @Autowired
    LikeMapper likeMapper;
    
    /**
     * 用户点赞
     */
    public void like(Long articleId){

        User userName = AuthContext.getCurrentUser();

        if(userName == null)
        {
            throw new NoPermissionException(Constants.NOT_LOGGED_IN);
        }

        Like like = likeMapper.find(userName.getId(), articleId);

        if(like != null)
        {
            throw new NoPermissionException(Constants.ALREADY_LIKED);
        }

        likeMapper.setId(userName.getId(),articleId);
        likeMapper.update(articleId);





    }

    /**
     * 取消点赞
     * @param articleId
     */
    public void disLike(Long articleId) {
        User userName = AuthContext.getCurrentUser();

        if(userName == null)
        {
            throw new NoPermissionException(Constants.NOT_LOGGED_IN);
        }
        Like like = likeMapper.find(userName.getId(), articleId);

        if(like == null)
        {
            throw new NoPermissionException(Constants.UNLIKED);
        }

        Long id = like.getId();
        likeMapper.deleteId(id);
        likeMapper.decrease(articleId);

    }
}
