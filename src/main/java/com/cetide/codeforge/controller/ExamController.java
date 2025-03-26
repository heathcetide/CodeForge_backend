package com.cetide.codeforge.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.model.entity.Chapter;
import com.cetide.codeforge.model.entity.exams.Exam;
import com.cetide.codeforge.model.entity.exams.SqlQuestion;
import com.cetide.codeforge.model.vo.ExamVO;
import com.cetide.codeforge.model.vo.SqlQuestionVO;
import com.cetide.codeforge.service.ChapterService;
import com.cetide.codeforge.service.ExamService;
import com.cetide.codeforge.service.SqlQuestionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * 测试模块
 *
 * @author heathcetide
 */
@RestController
@RequestMapping("/api/exam")
@Api(tags = "测试模块")
public class ExamController {

    private final ExamService examService;

    private final SqlQuestionService sqlQuestionService;

    public ExamController(ExamService examService, SqlQuestionService sqlQuestionService) {
        this.examService = examService;
        this.sqlQuestionService = sqlQuestionService;
    }


    @GetMapping("/chapter/{chapterId}")
    public ApiResponse<ExamVO> getExamByChapterId(@PathVariable int chapterId) {
        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getChapterId, chapterId);
        Exam one = examService.getOne(queryWrapper);
        if (one == null) {
            throw new ResourceNotFoundException("本章节无课后练习");
        }
        LambdaQueryWrapper<SqlQuestion> sqlQueryWrapper = new LambdaQueryWrapper<>();
        sqlQueryWrapper.eq(SqlQuestion::getExamId, one.getId());
        ExamVO examVO = new ExamVO();
        BeanUtil.copyProperties(one, examVO);
        examVO.setQuestions(sqlQuestionService.list(sqlQueryWrapper).stream().map(sqlQuestion -> BeanUtil.toBean(sqlQuestion, SqlQuestionVO.class)).collect(Collectors.toList()));
        return ApiResponse.success(examVO);
    }
}
