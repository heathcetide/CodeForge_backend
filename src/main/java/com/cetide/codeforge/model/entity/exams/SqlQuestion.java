package com.cetide.codeforge.model.entity.exams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sql_question")
public class SqlQuestion {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long examId;

    private String title;

    private String description;

    private String initSql; // 初始化数据库语句

    private String expectedSql; // 标准答案SQL

    private String expectedResultJson; // 正确结果的JSON表示

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitSql() {
        return initSql;
    }

    public void setInitSql(String initSql) {
        this.initSql = initSql;
    }

    public String getExpectedSql() {
        return expectedSql;
    }

    public void setExpectedSql(String expectedSql) {
        this.expectedSql = expectedSql;
    }

    public String getExpectedResultJson() {
        return expectedResultJson;
    }

    public void setExpectedResultJson(String expectedResultJson) {
        this.expectedResultJson = expectedResultJson;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}