package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.cetide.codeforge.model.entity.InterviewQuestionCategory;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Select;

/**
 * InterviewQuestionCategoryMapper
 *
 * @author heathcetide
 */
public interface InterviewQuestionCategoryMapper extends BaseMapper<InterviewQuestionCategory> {
    /**
     * 获取所有面试题分类
     * @Param 无
     * @Return List<InterviewQuestionCategory>
     */
    @Select("SELECT * FROM interview_question_category ${ew.customSqlSegment}")
    public List<InterviewQuestionCategory> selectList(@Param("ew") QueryWrapper<InterviewQuestionCategory> queryWrapper);

    /**
     * 获取所有面试题分类
     * @Param 无
     * @Return List<InterviewQuestionCategory>
     */
    @Select("SELECT * FROM interview_question_category")
    public List<InterviewQuestionCategory> selectAllCategories();
}
