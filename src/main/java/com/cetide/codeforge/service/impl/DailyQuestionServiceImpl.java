package com.cetide.codeforge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.mapper.DailyQuestionMapper;
import com.cetide.codeforge.model.entity.question.DailyQuestion;
import com.cetide.codeforge.model.entity.question.Question;
import com.cetide.codeforge.model.vo.DailyQuestionVO;
import com.cetide.codeforge.model.vo.QuestionVO;
import com.cetide.codeforge.service.DailyQuestionService;
import com.cetide.codeforge.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cetide.codeforge.model.vo.QuestionVO.objToVo;

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
            LambdaQueryWrapper<Question> questionQueryWrapper = new LambdaQueryWrapper<>();
            questionQueryWrapper.orderByAsc(Question::getId); // 先按任意字段排序（可选）
            questionQueryWrapper.last("ORDER BY RAND() LIMIT 1"); // 核心修改点
            Question question = questionService.getOne(questionQueryWrapper);
            dailyQuestion.setQuestionId(question.getId());
            dailyQuestion.setQuestionImg("https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/c0ca9d55-04a9-4014-b8d8-1a0174ffe46ewordCloud-1415979003.png");
            dailyQuestionMapper.insert(dailyQuestion);
        }
        try {
            Question question = questionService.getById(dailyQuestion.getQuestionId());
            DailyQuestionVO questionVO = new DailyQuestionVO();
            questionVO.setQuestion(objToVo(question));
            questionVO.setQuestionImg(dailyQuestion.getQuestionImg());
            return ApiResponse.success(questionVO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
