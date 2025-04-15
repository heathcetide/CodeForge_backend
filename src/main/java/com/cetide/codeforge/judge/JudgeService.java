package com.cetide.codeforge.judge;


import com.cetide.codeforge.model.entity.question.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
