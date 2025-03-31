package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.ArticleLike;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

    /**
     * 查询用户是否点赞
     * @param userId
     * @param articleId
     * @return
     */
    @Select("select * from article_like where user_id = #{userId} and article_id = #{articleId}")
    ArticleLike find(Long userId,Long articleId);

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
    @Insert("insert into article_like(user_id, article_id) values (#{userId},#{articleId})")
    void setId(Long userId,Long articleId);

    /**
     * 用户取消点赞
     * @param id
     */
    @Delete("delete from article_like where id = #{id}")
    void deleteId(Long id);

    /**
     * 点赞数加1
     * @param articleId
     */
    @Update("update article set like_count = like_count-1 where id = #{articleId}")
    void decrease(Long articleId);
}
