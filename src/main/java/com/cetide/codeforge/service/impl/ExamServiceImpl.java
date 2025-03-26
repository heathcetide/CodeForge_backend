package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.ExamMapper;
import com.cetide.codeforge.model.entity.exams.Exam;
import com.cetide.codeforge.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
}
