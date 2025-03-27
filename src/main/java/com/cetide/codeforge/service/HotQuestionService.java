package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.PageResult;
import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.model.entity.QuestionPopularity;
import com.cetide.codeforge.model.query.QuestionQuery;
import com.cetide.codeforge.model.vo.QuestionDetailVO;
import java.lang.Integer;

/**
 * 热度排行管理
 *
 * @author heathcetide
 */
public interface HotQuestionService extends IService<QuestionPopularity> {

    /**
     * 获取热门题目列表:根据题目热度分数查询热门题目列表
     *
     * @param questionQuery 热度排行查询对象
     * @return 问题列表
     */
    PageResult<InterviewQuestion> getHotQuestions(QuestionQuery questionQuery);

    /**
     * 获取热门题目详情:根据题目id查询题目详情及热度信息
     *
     * @param questionId questionID
     * @return  题目详情视图对象
     */
    QuestionDetailVO getHotQuestionDetail(Integer questionId);
}
