package com.cetide.codeforge.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.model.dto.questionSet.QuestionSetAddRequest;
import com.cetide.codeforge.model.dto.questionSet.QuestionSetQueryRequest;
import com.cetide.codeforge.model.entity.question.QuestionSet;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.QuestionSetVO;
import com.cetide.codeforge.service.QuestionSetService;
import com.cetide.codeforge.service.UserService;
import com.google.gson.Gson;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/question-set")
public class QuestionSetController {

    @Resource
    private QuestionSetService questionSetService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    /**
     * 创建
     */
    @PostMapping("/add")
    public ApiResponse<Long> addQuestion(@RequestBody QuestionSetAddRequest questionSetAddRequest, HttpServletRequest request) {
        if (questionSetAddRequest == null) {
            throw new BusinessException("参数错误");
        }
        QuestionSet questionSet = new QuestionSet();
        BeanUtils.copyProperties(questionSetAddRequest, questionSet);
        List<String> tags = questionSetAddRequest.getQuestionIds();
        if (tags != null) {
            questionSet.setQuestionIds(GSON.toJson(tags));
        }
        questionSetService.validQuestionSet(questionSet, true);
        User loginUser = AuthContext.getCurrentUser();
        questionSet.setUserId(loginUser.getId());
        questionSet.setUpdateTime(new Date());
        questionSet.setCreateTime(new Date());
        questionSet.setIsDelete(0);
        boolean result = questionSetService.save(questionSet);
        if (!result){
            throw new BusinessException("添加失败！");
        }
        long newQuestionId = questionSet.getId();
        return ApiResponse.success(newQuestionId);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param questionSetQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public ApiResponse<Page<QuestionSetVO>> listQuestionVOByPage(@RequestBody QuestionSetQueryRequest questionSetQueryRequest,
                                                                  HttpServletRequest request) {
        long current = questionSetQueryRequest.getCurrent();
        long size = questionSetQueryRequest.getPageSize();
        if (size > 20){
            throw new BusinessException("参数错误");
        }
        Page<QuestionSet> questionPage = questionSetService.page(new Page<>(current, size),
                questionSetService.getQueryWrapper(questionSetQueryRequest));
        return ApiResponse.success(questionSetService.getQuestionVOPage(questionPage, request));
    }
}
