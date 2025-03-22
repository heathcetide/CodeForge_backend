package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.SqlQuestionMapper;
import com.cetide.codeforge.model.entity.exams.SqlQuestion;
import com.cetide.codeforge.service.SqlQuestionService;
import org.springframework.stereotype.Service;

@Service
public class SqlQuestionServiceImpl extends ServiceImpl<SqlQuestionMapper, SqlQuestion> implements SqlQuestionService {
}