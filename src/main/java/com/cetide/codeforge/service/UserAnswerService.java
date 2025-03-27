package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.AnswerDTO;
import com.cetide.codeforge.model.entity.UserAnswer;
import com.cetide.codeforge.model.query.AnswerQuery;
import java.lang.Boolean;
import java.lang.String;

/**
 * 实时答题及反馈管理
 *
 * @author heathcetide
 */
public interface UserAnswerService extends IService<UserAnswer> {

    /**
     * 实时答题
     *
     * @param answerDTO 用户答题输入参数
     */
    Boolean submitAnswer(AnswerDTO answerDTO);

    /**
     * 获取答题反馈
     *
     * @param answerQuery 获取答题反馈输入参数
     */
    String getAnswerFeedback(AnswerQuery answerQuery);
}
