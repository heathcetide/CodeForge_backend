package com.cetide.codeforge.service.impl;

import com.cetide.codeforge.mapper.InterviewQuestionMapper;
import com.cetide.codeforge.model.dto.RandomQuestionDTO;
import com.cetide.codeforge.model.dto.RandomTopicQuestionDTO;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.service.RandomQuestionService;
import java.lang.Override;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cetide.codeforge.exception.BusinessException;
import java.util.Random;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Map;
import com.cetide.codeforge.common.constants.ResultCodeConstant;

/**
 * 随机提问管理的实现
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
@Service
public class RandomQuestionServiceImpl implements RandomQuestionService {

    @Autowired
    private InterviewQuestionMapper interviewQuestionMapper;

    @Override
    public InterviewQuestion getRandomQuestion(RandomQuestionDTO randomQuestionDTO) {
        try {
            List<InterviewQuestion> questionList = interviewQuestionMapper.selectList(Wrappers.emptyWrapper());
            if (questionList.isEmpty()) {
                throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
            }
            Random random = new Random();
            InterviewQuestion randomQuestion = questionList.get(random.nextInt(questionList.size()));
            return randomQuestion;
        } catch (Exception e) {
            throw new BusinessException(ResultCodeConstant.CODE_999999_MSG);
        }
    }

    @Override
    public InterviewQuestion getRandomTopicQuestion(RandomTopicQuestionDTO randomTopicQuestionDTO) {
        try {
            QueryWrapper<InterviewQuestion> queryWrapper = Wrappers.query();
            queryWrapper.eq("topic_id", randomTopicQuestionDTO.getTopicId());
            List<InterviewQuestion> questionList = interviewQuestionMapper.selectList(queryWrapper);
            if (questionList.isEmpty()) {
                throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
            }
            Random random = new Random();
            InterviewQuestion randomQuestion = questionList.get(random.nextInt(questionList.size()));
            return randomQuestion;
        } catch (Exception e) {
            throw new BusinessException(ResultCodeConstant.CODE_999999_MSG);
        }
    }
}
