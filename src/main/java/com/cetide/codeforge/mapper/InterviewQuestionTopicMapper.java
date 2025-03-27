package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.cetide.codeforge.model.entity.InterviewQuestionTopic;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Select;

/**
 * InterviewQuestionTopicMapper
 *
 * @author heathcetide
 */
public interface InterviewQuestionTopicMapper extends BaseMapper<InterviewQuestionTopic> {

    /**
     * 获取所有面试题专题
     */
    @Select("SELECT * FROM interview_question_topic ${ew.customSqlSegment}")
    List<InterviewQuestionTopic> selectList(@Param("ew") QueryWrapper<InterviewQuestionTopic> queryWrapper);
}
