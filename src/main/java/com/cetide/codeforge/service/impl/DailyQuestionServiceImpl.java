package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.mapper.DailyQuestionMapper;
import com.cetide.codeforge.model.entity.question.DailyQuestion;
import com.cetide.codeforge.model.entity.question.Question;
import com.cetide.codeforge.model.vo.DailyQuestionVO;
import com.cetide.codeforge.service.DailyQuestionService;
import com.cetide.codeforge.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author heathcetide
*/
@Service
public class DailyQuestionServiceImpl extends ServiceImpl<DailyQuestionMapper, DailyQuestion>
implements DailyQuestionService {

    @Resource
    private DailyQuestionMapper dailyQuestionMapper;

    @Resource
    private QuestionService questionService;

    @Override
    public ApiResponse<DailyQuestionVO> getDailyQuestion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        DailyQuestion dailyQuestion = dailyQuestionMapper.selectByDate(formattedDate);
        if (dailyQuestion == null) {
            dailyQuestion = new DailyQuestion();
            dailyQuestion.setDate(new Date());
            dailyQuestion.setQuestionId(questionService.list().get(0).getId());
            dailyQuestion.setQuestionImg("https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/c0ca9d55-04a9-4014-b8d8-1a0174ffe46ewordCloud-1415979003.png");
            dailyQuestionMapper.insert(dailyQuestion);
        }
        Question question = questionService.getById(dailyQuestion.getQuestionId());
        DailyQuestionVO questionVO = new DailyQuestionVO();
        questionVO.setQuestion(question);
        questionVO.setQuestionImg(dailyQuestion.getQuestionImg());
        return ApiResponse.success(questionVO);
    }
}
