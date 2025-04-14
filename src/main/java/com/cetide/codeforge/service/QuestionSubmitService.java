package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cetide.codeforge.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.cetide.codeforge.model.entity.question.QuestionSubmit;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.QuestionSubmitVO;

/**
* @author heathcetide
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    
    /**
     * 题目提交
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
