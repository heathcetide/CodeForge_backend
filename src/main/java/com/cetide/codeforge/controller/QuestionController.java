package com.cetide.codeforge.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.model.dto.DeleteRequest;
import com.cetide.codeforge.model.dto.question.*;
import com.cetide.codeforge.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cetide.codeforge.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.cetide.codeforge.model.entity.question.Question;
import com.cetide.codeforge.model.entity.question.QuestionSubmit;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.DailyQuestionVO;
import com.cetide.codeforge.model.vo.QuestionSubmitVO;
import com.cetide.codeforge.model.vo.QuestionVO;
import com.cetide.codeforge.service.DailyQuestionService;
import com.cetide.codeforge.service.QuestionService;
import com.cetide.codeforge.service.QuestionSubmitService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cetide.codeforge.model.vo.QuestionSubmitVO.objToVo;

/**
 * 题目接口
 */
@RestController
@RequestMapping("/api/question")
@Api(tags = "oj题目模块")
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionSubmitService questionSubmitService;

    private final DailyQuestionService dailyQuestionService;

    private final static Gson GSON = new Gson();

    public QuestionController(QuestionService questionService, QuestionSubmitService questionSubmitService, DailyQuestionService dailyQuestionService) {
        this.questionService = questionService;
        this.questionSubmitService = questionSubmitService;
        this.dailyQuestionService = dailyQuestionService;
    }

    /**
     * 创建
     */
    @PostMapping("/add")
    @ApiOperation("新建题目")
    public ApiResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        if (questionAddRequest == null) {
            throw new BusinessException("PARAMS_ERROR");
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        questionService.validQuestion(question, true);
        User loginUser = AuthContext.getCurrentUser();
        question.setUserId(loginUser.getId());
        question.setFavourNum(0);
        question.setThumbNum(0);
        boolean result = questionService.save(question);
        if (!result) {
            throw new BusinessException("SAVE_ERROR");
        }
        long newQuestionId = question.getId();
        return ApiResponse.success(newQuestionId);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除题目")
    public ApiResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        User user = AuthContext.getCurrentUser();
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        if (oldQuestion == null) {
            throw new BusinessException("NOT_FOUND_ERROR");
        }
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId())) {
            throw new BusinessException("NO_AUTH_ERROR");
        }
        return ApiResponse.success(questionService.removeById(id));
    }

    /**
     * 更新（仅管理员）
     */
    @PostMapping("/update")
    @ApiOperation("更新题目")
    public ApiResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<JudgeCase> judgeCase = questionUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        if (oldQuestion == null) {
            throw new BusinessException("NOT_FOUND_ERROR");
        }
        boolean result = questionService.updateById(question);
        return ApiResponse.success(result);
    }

    /**
     * 根据 id 获取
     */
    @GetMapping("/get")
    @ApiOperation("根据id获取[需要登录]")
    public ApiResponse<Question> getQuestionById(long id) {
        if (id <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException("NOT_FOUND_ERROR");
        }
        User loginUser = AuthContext.getCurrentUser();
        if (!question.getUserId().equals(loginUser.getId())) {
            throw new BusinessException("NO_AUTH_ERROR");
        }
        return ApiResponse.success(question);
    }

    /**
     * 根据 id 获取（脱敏）
     */
    @GetMapping("/get/vo")
    @ApiOperation("根据id获取（脱敏）[无需登录]")
    public ApiResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException("NOT_FOUND_ERROR");
        }
        return ApiResponse.success(questionService.getQuestionVO(question, request));
    }

    /**
     * 分页获取列表（封装类）
     */
    @GetMapping("/list/page/vo")
    @ApiOperation("分页获取列表（封装类）")
    public ApiResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestParam(required = false) Long current,
                                                              @RequestParam(required = false) Long pageSize,
                                                              @RequestParam(required = false) String sortField,
                                                              @RequestParam(required = false, defaultValue = "ascend") String sortOrder,
                                                              @RequestParam(required = false) Long id,
                                                              @RequestParam(required = false) String title,
                                                              @RequestParam(required = false) String content,
                                                              @RequestParam(required = false) List<String> tags,
                                                              @RequestParam(required = false) String answer,
                                                              @RequestParam(required = false) Long userId,
                                                              HttpServletRequest request) {
        // 默认值设置
        if (current == null) {
            current = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }

        // 限制爬虫
        if (pageSize > 20) {
            throw new BusinessException("PAGE_SIZE_ERROR");
        }

        // 创建 QuestionQueryRequest 对象
        QuestionQueryRequest questionQueryRequest = new QuestionQueryRequest();
        questionQueryRequest.setCurrent(current);
        questionQueryRequest.setPageSize(pageSize);
        questionQueryRequest.setSortField(sortField);
        questionQueryRequest.setSortOrder(sortOrder);
        questionQueryRequest.setId(id);
        questionQueryRequest.setTitle(title);
        questionQueryRequest.setContent(content);
        questionQueryRequest.setTags(tags);
        questionQueryRequest.setAnswer(answer);
        questionQueryRequest.setUserId(userId);

        Page<Question> questionPage = questionService.page(new Page<>(current, pageSize),
                questionService.getQueryWrapper(questionQueryRequest));

        return ApiResponse.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     */
    @PostMapping("/my/list/page/vo")
    @ApiOperation("分页获取当前用户创建的资源列表")
    public ApiResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                                HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException("PARAMS_ERROR");
        }
        User loginUser = AuthContext.getCurrentUser();
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        if (size > 20) {
            throw new BusinessException("PAGE_SIZE_ERROR");
        }
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ApiResponse.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * 分页获取题目列表（仅管理员）
     */
    @PostMapping("/list/page")
    @ApiOperation("分页获取题目列表（仅管理员）")
    public ApiResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ApiResponse.success(questionPage);
    }

    /**
     * 编辑（用户）
     */
    @PostMapping("/edit")
    @ApiOperation("编辑（用户）")
    public ApiResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<String> tags = questionEditRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionEditRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionEditRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        User loginUser = AuthContext.getCurrentUser();
        long id = questionEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        if (oldQuestion == null) {
            throw new BusinessException("NOT_FOUND_ERROR");
        }
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId())) {
            throw new BusinessException("NO_AUTH_ERROR");
        }
        boolean result = questionService.updateById(question);
        return ApiResponse.success(result);
    }

    /**
     * 提交题目
     */
    @PostMapping("/question_submit/do")
    @ApiOperation("提交题目")
    public ApiResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException("PARAMS_ERROR");
        }
        User loginUser = AuthContext.getCurrentUser();
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ApiResponse.success(questionSubmitId);
    }

    /**
     * 根据id获取提交结果
     */
    @GetMapping("/submit/result/{id}")
    @ApiOperation("根据id获取提交结果")
    public ApiResponse<QuestionSubmit> getQuestionSubmitVO(@PathVariable("id") long id) {
        User loginUser = AuthContext.getCurrentUser();
        LambdaQueryWrapper<QuestionSubmit> lambdaQueryWrapper = new LambdaQueryWrapper<QuestionSubmit>()
                .eq(QuestionSubmit::getId, id)
                .eq(QuestionSubmit::getUserId, loginUser.getId());
        QuestionSubmit questionSubmit = questionSubmitService.getOne(lambdaQueryWrapper);
        return ApiResponse.success(questionSubmit);
    }

    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     */
    @PostMapping("/question_submit/list/page")
    @ApiOperation("分页获取题目提交列表")
    public ApiResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = AuthContext.getCurrentUser();
        // 返回脱敏信息
        return ApiResponse.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

    /**
     * 获取每日一题
     */
    @GetMapping("/daily-question")
    @ApiOperation("获取每日一题")
    public ApiResponse<DailyQuestionVO> getDailyQuestion() {
        // 假设每日题目只会有一个，查找当日题目
        return dailyQuestionService.getDailyQuestion();
    }

    /**
     * 获取所有的题目
     */
    @GetMapping("/get/all")
    @ApiOperation("获取所有的题目")
    public ApiResponse<List<Question>> getAllQuestions() {
        return ApiResponse.success(questionService.list());
    }
}
