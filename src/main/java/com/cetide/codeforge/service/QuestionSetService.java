package com.cetide.codeforge.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.questionSet.QuestionSetQueryRequest;
import com.cetide.codeforge.model.entity.question.QuestionSet;
import com.cetide.codeforge.model.vo.QuestionSetVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author heathcetide
*/
public interface QuestionSetService extends IService<QuestionSet> {

    void validQuestionSet(QuestionSet questionSet, boolean b);

    Page<QuestionSetVO> getQuestionVOPage(Page<QuestionSet> questionPage);

    Wrapper<QuestionSet> getQueryWrapper(QuestionSetQueryRequest questionSetQueryRequest);
}
