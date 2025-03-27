package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.InterviewQuestionMapper;
import com.cetide.codeforge.mapper.QuestionPopularityMapper;
import com.cetide.codeforge.model.dto.PageResult;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.QuestionPopularity;
import com.cetide.codeforge.model.query.QuestionQuery;
import com.cetide.codeforge.model.vo.QuestionDetailVO;
import com.cetide.codeforge.service.HotQuestionService;
import java.lang.Integer;
import java.lang.Override;

import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cetide.codeforge.exception.BusinessException;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.stream.Collectors;

import com.cetide.codeforge.common.constants.ResultCodeConstant;

/**
 * 热度排行管理的实现
 *
 * @author heathcetide
 */
@Service
public class HotQuestionServiceImpl extends ServiceImpl<QuestionPopularityMapper, QuestionPopularity> implements HotQuestionService {

    private final InterviewQuestionMapper interviewQuestionMapper;

    private final QuestionPopularityMapper questionPopularityMapper;

    public HotQuestionServiceImpl(InterviewQuestionMapper interviewQuestionMapper, QuestionPopularityMapper questionPopularityMapper) {
        this.interviewQuestionMapper = interviewQuestionMapper;
        this.questionPopularityMapper = questionPopularityMapper;
    }

    @Override
    public PageResult<InterviewQuestion> getHotQuestions(QuestionQuery questionQuery) {
        QueryWrapper<QuestionPopularity> queryWrapper = Wrappers.query();
        queryWrapper.orderByDesc("popularity_score").last("LIMIT " + questionQuery.getLimit());
        List<QuestionPopularity> popularQuestions = questionPopularityMapper.selectList(queryWrapper);
        List<Integer> questionIds = popularQuestions.stream().map(QuestionPopularity::getQuestionId).collect(Collectors.toList()); // 修改这一行
        QueryWrapper<InterviewQuestion> questionWrapper = Wrappers.query();
        questionWrapper.in("question_id", questionIds);
        List<InterviewQuestion> questions = interviewQuestionMapper.selectList(questionWrapper);
//        PageResult<InterviewQuestion> pageResult = new PageResult<>(questions, questions.size());
//        return pageResult;
        return null;
    }

    @Override
    public QuestionDetailVO getHotQuestionDetail(Integer questionId) {
        InterviewQuestion questionDO = interviewQuestionMapper.selectById(questionId);
        if (questionDO == null) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        QuestionPopularity popularityDO = questionPopularityMapper.selectById(questionId);
        if (popularityDO == null) {
            popularityDO = new QuestionPopularity();
            popularityDO.setQuestionId(questionId);
            popularityDO.setPopularityScore(0);
        }
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        BeanUtils.copyProperties(questionDO, questionDetailVO);
        questionDetailVO.setPopularityScore(popularityDO.getPopularityScore());
        questionDetailVO.setLastTested(popularityDO.getLastTested());
        return questionDetailVO;
    }
}
