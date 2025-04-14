package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.model.entity.question.DailyQuestion;
import com.cetide.codeforge.model.vo.DailyQuestionVO;


/**
* @author heathcetide
*/
public interface DailyQuestionService extends IService<DailyQuestion> {

    ApiResponse<DailyQuestionVO> getDailyQuestion();
}
