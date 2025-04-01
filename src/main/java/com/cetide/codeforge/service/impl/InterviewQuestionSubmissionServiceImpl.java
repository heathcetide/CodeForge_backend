package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.InterviewQuestionSubmissionMapper;
import com.cetide.codeforge.model.entity.interview.InterviewQuestionSubmission;
import com.cetide.codeforge.service.InterviewQuestionSubmissionService;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionSubmissionServiceImpl extends ServiceImpl<InterviewQuestionSubmissionMapper, InterviewQuestionSubmission>
        implements InterviewQuestionSubmissionService {

}
