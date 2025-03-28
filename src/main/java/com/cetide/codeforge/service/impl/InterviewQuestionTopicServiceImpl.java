package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.InterviewQuestionTopicMapper;
import com.cetide.codeforge.model.entity.InterviewQuestionTopic;
import com.cetide.codeforge.service.InterviewQuestionTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionTopicServiceImpl extends ServiceImpl<InterviewQuestionTopicMapper, InterviewQuestionTopic>
        implements InterviewQuestionTopicService {

    private final InterviewQuestionTopicMapper topicMapper;

    public InterviewQuestionTopicServiceImpl(InterviewQuestionTopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }
}
