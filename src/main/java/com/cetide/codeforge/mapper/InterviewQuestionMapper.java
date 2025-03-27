package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.cetide.codeforge.model.entity.InterviewQuestion;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Select;

/**
 * InterviewQuestionMapper
 *
 * @author heathcetide
 */
public interface InterviewQuestionMapper extends BaseMapper<InterviewQuestion> {

    /**
     * 根据分类获取面试题列表
     *
     * @param categoryId 类别ID
     * @return 题目列表
     */
    @Select("SELECT * FROM interview_question WHERE category_id = #{categoryId}")
    List<InterviewQuestion> selectQuestionsByCategoryId(@Param("categoryId") Integer categoryId);


    /**
     * 根据专题ID查询题目列表
     *
     * @param topicId 专题ID
     * @return 题目列表
     */
    @Select("SELECT * FROM interview_question WHERE topic_id = #{topicId}")
    List<InterviewQuestion> selectQuestionsByTopicId(@Param("topicId") Integer topicId);

    /**
     * 查询所有题目列表
     *
     * @return 所有题目列表
     */
    @Select("SELECT * FROM interview_question")
    List<InterviewQuestion> selectAllQuestions();

    /**
     * 使用 QueryWrapper 获取面试题列表
     *
     * @param queryWrapper 查询条件
     * @return 题目列表
     */
    @Select("SELECT * FROM interview_question ${ew.customSqlSegment}")
    List<InterviewQuestion> selectList(@Param("ew") QueryWrapper<InterviewQuestion> queryWrapper);
}
