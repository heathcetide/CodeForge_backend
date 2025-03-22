package com.cetide.codeforge.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.exams.SqlQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SqlQuestionMapper extends BaseMapper<SqlQuestion> {
}
