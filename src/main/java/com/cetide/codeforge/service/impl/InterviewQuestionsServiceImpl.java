package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.InterviewQuestionMapper;
import com.cetide.codeforge.mapper.InterviewQuestionTopicMapper;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.InterviewQuestionTopic;
import com.cetide.codeforge.model.query.InterviewQuestionQuery;
import com.cetide.codeforge.service.InterviewQuestionsService;
import java.lang.Override;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * InterviewQuestionsServiceImpl
 *
 * @author heathcetide
 */
@Service
public class InterviewQuestionsServiceImpl extends ServiceImpl<InterviewQuestionMapper, InterviewQuestion> implements InterviewQuestionsService {

    private final InterviewQuestionMapper questionMapper;

    public InterviewQuestionsServiceImpl(InterviewQuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public List<InterviewQuestion> getQuestionsByCategory(InterviewQuestionQuery query) {
        QueryWrapper<InterviewQuestion> wrapper = Wrappers.query();
        wrapper.eq("category_id", query.getCategoryId());
        List<InterviewQuestion> questions = questionMapper.selectList(wrapper);
        return questions;
    }

    @Override
    public List<InterviewQuestion> getQuestionsByTopic(InterviewQuestionQuery query) {
        QueryWrapper<InterviewQuestion> wrapper = Wrappers.query();
        wrapper.eq("topic_id", query.getTopicId());
        List<InterviewQuestion> questions = questionMapper.selectList(wrapper);
        return questions;
    }

    @Override
    public List<InterviewQuestion> getRandomInterviewQuestions() {
        return questionMapper.getRandomInterviewQuestions(15);
    }
}
