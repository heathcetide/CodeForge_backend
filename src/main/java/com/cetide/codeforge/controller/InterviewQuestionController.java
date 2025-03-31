package com.cetide.codeforge.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.InterviewQuestionTopic;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.InterviewQuestionListVO;
import com.cetide.codeforge.model.vo.InterviewQuestionTopicVO;
import com.cetide.codeforge.model.vo.InterviewQuestionVO;
import com.cetide.codeforge.service.InterviewQuestionTopicService;
import com.cetide.codeforge.service.InterviewQuestionsService;
import com.cetide.codeforge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interview")
@Api(tags = "面试模块")
public class InterviewQuestionController {

    private final InterviewQuestionsService interviewQuestionsService;

    private final InterviewQuestionTopicService interviewQuestionTopicService;

    private final UserService userService;

    public InterviewQuestionController(
            InterviewQuestionsService interviewQuestionsService, InterviewQuestionTopicService interviewQuestionTopicService, UserService userService) {
        this.interviewQuestionsService = interviewQuestionsService;
        this.interviewQuestionTopicService = interviewQuestionTopicService;
        this.userService = userService;
    }

    @GetMapping("/list/{topicId}")
    @ApiOperation("根据TopicId获取面试题列表")
    public ApiResponse<List<InterviewQuestionListVO>> list(@PathVariable Long topicId) {
        LambdaQueryWrapper<InterviewQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterviewQuestion::getTopicId, topicId);
        return ApiResponse.success(interviewQuestionsService.list(queryWrapper).stream().map(interviewQuestion -> BeanUtil.toBean(interviewQuestion, InterviewQuestionListVO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询面试题")
    public ApiResponse<InterviewQuestionVO> getInterviewQuestion(@PathVariable("id") String id) {
        LambdaQueryWrapper<InterviewQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterviewQuestion::getId, id);
        InterviewQuestion interviewQuestion = interviewQuestionsService.getOne(queryWrapper);
        if (interviewQuestion == null) {
            throw new ResourceNotFoundException("没有这篇文章");
        }
        InterviewQuestionVO bean = BeanUtil.toBean(interviewQuestion, InterviewQuestionVO.class);
        User userById = userService.getUserById(interviewQuestion.getCreateBy());
        bean.setUsername(userById.getUsername());
        bean.setAvatar(userById.getAvatar());
        bean.setUserId(userById.getId());
        return ApiResponse.success(bean);
    }

    @GetMapping("/topic/list")
    @ApiOperation("专题列表")
    public ApiResponse<List<InterviewQuestionTopicVO>> getInterviewQuestionTopicList() {
        return ApiResponse.success(interviewQuestionTopicService.list().stream().map(interviewQuestionTopic -> BeanUtil.toBean(interviewQuestionTopic, InterviewQuestionTopicVO.class)).collect(Collectors.toList()));
    }
}
