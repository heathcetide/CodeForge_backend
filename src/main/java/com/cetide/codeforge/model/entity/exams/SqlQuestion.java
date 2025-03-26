package com.cetide.codeforge.model.entity.exams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * sql问题实体类
 *
 * @author heathcetide
 */
@TableName("sql_question")
public class SqlQuestion {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * exam_id
     */
    @TableField("exam_id")
    private Long examId;

    /**
     * title
     */
    @TableField("title")
    private String title;

    /**
     * description
     */
    @TableField("description")
    private String description;

    /**
     * schema_sql
     */
    @TableField("schema_sql")
    private String schemaSql;

    /**
     * seed_data_sql
     */
    @TableField("seed_data_sql")
    private String seedDataSql;

    /**
     * expected_json
     */
    @TableField("expected_json")
    private String expectedJson;

    /**
     * answer_sql
     */
    @TableField("answer_sql")
    private String answerSql;

    /**
     * score
     */
    @TableField("score")
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

    public String getSchemaSql() {
        return schemaSql;
    }

    public void setSchemaSql(String schemaSql) {
        this.schemaSql = schemaSql;
    }

    public String getSeedDataSql() {
        return seedDataSql;
    }

    public void setSeedDataSql(String seedDataSql) {
        this.seedDataSql = seedDataSql;
    }

    public String getExpectedJson() {
        return expectedJson;
    }

    public void setExpectedJson(String expectedJson) {
        this.expectedJson = expectedJson;
    }

    public String getAnswerSql() {
        return answerSql;
    }

    public void setAnswerSql(String answerSql) {
        this.answerSql = answerSql;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}