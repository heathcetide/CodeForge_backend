package com.cetide.blog.mapper;

import com.cetide.blog.model.entity.Like;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {

    /**
     * 查询用户是否点赞
     * @param userId
     * @param articleId
     * @return
     */
    @Select("select * from like_article where user_id = #{userId} and article_id = #{articleId}")
    Like find(Long userId,Long articleId);

    /**
     * 点赞数加1
     * @param articleId
     */
    @Update("update article set like_count = like_count+1 where id = #{articleId}")
    void update(Long articleId);

    /**
     * 记录为已点赞
     * @param userId
     * @param articleId
     */
    @Insert("insert into like_article(user_id, article_id) values (#{userId},#{articleId})")
    void setId(Long userId,Long articleId);

    /**
     * 用户取消点赞
     * @param id
     */
    @Delete("delete from like_article where id = #{id}")
    void deleteId(Long id);

    /**
     * 点赞数加1
     * @param articleId
     */
    @Update("update article set like_count = like_count-1 where id = #{articleId}")
    void decrease(Long articleId);
}
