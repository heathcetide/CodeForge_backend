package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.InterviewQuestionMapper;
import com.cetide.codeforge.mapper.QuestionPopularityMapper;
import com.cetide.codeforge.mapper.UserAnswerMapper;
import com.cetide.codeforge.model.dto.AnswerDTO;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.QuestionPopularity;
import com.cetide.codeforge.model.entity.UserAnswer;
import com.cetide.codeforge.model.query.AnswerQuery;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.String;

import com.cetide.codeforge.service.UserAnswerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.common.constants.ResultCodeConstant;
import java.util.Date;

/**
 * 实时答题及反馈管理的实现
 *
 * @author heathcetide
 */
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer> implements UserAnswerService {

    private final InterviewQuestionMapper interviewQuestionMapper;

    private final UserAnswerMapper userAnswerMapper;

    private final QuestionPopularityMapper questionPopularityMapper;

    public UserAnswerServiceImpl(InterviewQuestionMapper interviewQuestionMapper, UserAnswerMapper userAnswerMapper, QuestionPopularityMapper questionPopularityMapper) {
        this.interviewQuestionMapper = interviewQuestionMapper;
        this.userAnswerMapper = userAnswerMapper;
        this.questionPopularityMapper = questionPopularityMapper;
    }

    @Override
    public Boolean submitAnswer(AnswerDTO answerDTO) {
        // 获取题目答案
        InterviewQuestion question = interviewQuestionMapper.selectById(answerDTO.getQuestionId());
        UserAnswer userAnswerDO = getUserAnswer(answerDTO, question);
        int insertResult = userAnswerMapper.insert(userAnswerDO);
        if (insertResult <= 0) {
            throw new BusinessException(ResultCodeConstant.CODE_999999_MSG);
        }
        // 更新题目热度表中的热度分数和最后被测试的时间
        QuestionPopularity questionPopularityDO = questionPopularityMapper.selectOne(Wrappers.<QuestionPopularity>lambdaQuery().eq(QuestionPopularity::getQuestionId, answerDTO.getQuestionId()));
        if (questionPopularityDO == null) {
            questionPopularityDO = new QuestionPopularity();
            questionPopularityDO.setQuestionId(answerDTO.getQuestionId());
            questionPopularityDO.setPopularityScore(1);
            questionPopularityDO.setLastTested(new Date());
            questionPopularityMapper.insert(questionPopularityDO);
        } else {
            questionPopularityDO.setPopularityScore(questionPopularityDO.getPopularityScore() + 1);
            questionPopularityDO.setLastTested(new Date());
            questionPopularityMapper.updateById(questionPopularityDO);
        }
        return true;
    }

    private static @NotNull UserAnswer getUserAnswer(AnswerDTO answerDTO, InterviewQuestion question) {
        if (question == null) {
            throw new BusinessException(ResultCodeConstant.CODE_999999_MSG);
        }
        // 比较用户答案与正确答案
        boolean isCorrect = question.getAnswerText().equals(answerDTO.getUserAnswer());
        // 将用户答案及是否正确记录保存到用户作答记录表
        UserAnswer userAnswerDO = new UserAnswer();
        userAnswerDO.setUserId(answerDTO.getUserId());
        userAnswerDO.setQuestionId(answerDTO.getQuestionId());
        userAnswerDO.setUserAnswer(answerDTO.getUserAnswer());
        userAnswerDO.setIsCorrect(isCorrect);
        userAnswerDO.setAnswerTime(new Date());
        return userAnswerDO;
    }

    @Override
    public String getAnswerFeedback(AnswerQuery answerQuery) {
        // 根据userId和questionId从用户作答记录表中获取答题记录
        UserAnswer userAnswerDO = userAnswerMapper.selectOne(Wrappers.<UserAnswer>lambdaQuery().eq(UserAnswer::getUserId, answerQuery.getUserId()).eq(UserAnswer::getQuestionId, answerQuery.getQuestionId()));
        if (userAnswerDO == null) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        // 根据答题记录中的is_correct字段返回反馈信息
        return userAnswerDO.getIsCorrect() ? "回答正确" : "回答错误";
    }
}
