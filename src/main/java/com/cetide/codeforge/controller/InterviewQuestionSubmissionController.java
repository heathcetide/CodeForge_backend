package com.cetide.codeforge.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.model.dto.InterviewQuestionSubmissionDTO;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.interview.InterviewQuestionSubmission;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.InterviewQuestionSubmissionService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview-submission")
public class InterviewQuestionSubmissionController {

    private final InterviewQuestionSubmissionService interviewQuestionSubmissionService;

    public InterviewQuestionSubmissionController(InterviewQuestionSubmissionService interviewQuestionSubmissionService) {
        this.interviewQuestionSubmissionService = interviewQuestionSubmissionService;
    }

    @PostMapping
    public ApiResponse<String> submitAnswer(@RequestBody InterviewQuestionSubmissionDTO dto) {
        User user = AuthContext.getCurrentUser();
        InterviewQuestionSubmission interviewQuestionSubmission = BeanUtil.toBean(dto, InterviewQuestionSubmission.class);
        interviewQuestionSubmission.setUserId(user.getId());
        boolean save = interviewQuestionSubmissionService.save(interviewQuestionSubmission);
        if (save){
            return ApiResponse.success("提交答案成功");
        }
        return ApiResponse.error(500,"提交答案失败");
    }


    @GetMapping("/list/{questionId}")
    public ApiResponse<List<InterviewQuestionSubmission>> listAnswer(@PathVariable String questionId) {
        LambdaQueryWrapper<InterviewQuestionSubmission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterviewQuestionSubmission::getQuestionId, questionId);
        List<InterviewQuestionSubmission> interviewQuestionSubmission = interviewQuestionSubmissionService.list(queryWrapper);
        return ApiResponse.success(interviewQuestionSubmission);
    }
}
