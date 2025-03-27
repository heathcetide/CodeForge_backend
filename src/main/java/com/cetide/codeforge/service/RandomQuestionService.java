package com.cetide.codeforge.service;

import com.cetide.codeforge.model.dto.RandomQuestionDTO;
import com.cetide.codeforge.model.dto.RandomTopicQuestionDTO;
import com.cetide.codeforge.model.entity.InterviewQuestion;

/**
 * 随机提问管理
 *
 * @author heathcetide
 */
public interface RandomQuestionService {

    /**
     * 全量随机出题:根据用户id从题库中随机选择一道题目
     *
     * @param randomQuestionDTO 随机问题查询对象
     * @return  面试题实体对象
     */
    InterviewQuestion getRandomQuestion(RandomQuestionDTO randomQuestionDTO);

    /**
     * 专题随机出题:根据用户id和指定的专题id从题库中随机选择一道题目
     *
     * @param randomTopicQuestionDTO 专题随机问题查询对象
     * @return  面试题实体对象
     */
    InterviewQuestion getRandomTopicQuestion(RandomTopicQuestionDTO randomTopicQuestionDTO);
}
