package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.InterviewQuestionTopic;
import com.cetide.codeforge.model.query.InterviewQuestionQuery;
import java.util.List;

/**
 * InterviewQuestionsService
 *
 * @author heathcetide
 */
public interface InterviewQuestionsService extends IService<InterviewQuestion> {

    /**
     * questions-by-category
     *
     * @param query 面试题查询对象
     */
    List<InterviewQuestion> getQuestionsByCategory(InterviewQuestionQuery query);

    /**
     * questions-by-topic
     *
     * @param query 面试题查询对象
     */
    List<InterviewQuestion> getQuestionsByTopic(InterviewQuestionQuery query);

    /**
     * 随机获取面试题
     */
    List<InterviewQuestion> getRandomInterviewQuestions();
}
