package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.question.QuestionQueryRequest;
import com.cetide.codeforge.model.entity.question.Question;
import com.cetide.codeforge.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author heatcetide
*/
public interface QuestionService extends IService<Question> {


    /**
     * 校验
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
    
}
