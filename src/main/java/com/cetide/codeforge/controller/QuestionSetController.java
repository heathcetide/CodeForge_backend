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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/question-set")
public class QuestionSetController {

    @Resource
    private QuestionSetService questionSetService;

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
     */
    @GetMapping("/list/page/vo")
    public ApiResponse<Page<QuestionSetVO>> listQuestionSetVOByPage(
            @RequestParam(required = false) Long current,
            @RequestParam(required = false) Long pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false, defaultValue = "ascend") String sortOrder,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) Long userId) {

        // 默认值设置
        if (current == null) {
            current = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }

        // 限制分页大小
        if (pageSize > 20) {
            throw new BusinessException("参数错误");
        }

        // 创建 QuestionSetQueryRequest 对象
        QuestionSetQueryRequest questionSetQueryRequest = new QuestionSetQueryRequest();
        questionSetQueryRequest.setCurrent(current);
        questionSetQueryRequest.setPageSize(pageSize);
        questionSetQueryRequest.setSortField(sortField);
        questionSetQueryRequest.setSortOrder(sortOrder);
        questionSetQueryRequest.setId(id);
        questionSetQueryRequest.setName(name);
        questionSetQueryRequest.setDescription(description);
        questionSetQueryRequest.setTags(tags);
        questionSetQueryRequest.setUserId(userId);

        // 查询数据
        Page<QuestionSet> questionPage = questionSetService.page(new Page<>(current, pageSize),
                questionSetService.getQueryWrapper(questionSetQueryRequest));

        return ApiResponse.success(questionSetService.getQuestionVOPage(questionPage));
    }

}
