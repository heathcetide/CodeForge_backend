package com.cetide.codeforge.model.entity.exams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 考试表
 */
@TableName("exam") // 关联数据库表名
public class Exam {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联章节id
     */
    @TableField("chapter_id")
    private Long chapterId;

    /**
     * 测试种类
     */
    @TableField("exam_type")
    private String examType;

    /**
     * 通过分数
     */
    @TableField("pass_score")
    private Integer passScore;

    /**
     * 测试描述
     */
    @TableField("description")
    private String description;

    /**
     * 最多尝试次数
     */
    @TableField("max_attempts")
    private Integer maxAttempts;

    /**
     * 测试开放时间
     */
    @TableField("available_from")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date availableFrom;

    /**
     * 测试截至时间
     */
    @TableField("available_until")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date availableUntil;

    /**
     * 是否乱序题目
     */
    @TableField("shuffle_questions")
    private Integer shuffleQuestions;

    /**
     * 是否显示正确答案
     */
    @TableField("show_answers")
    private Integer showAnswers;

    /**
     * 监考配置（一般无）
     */
    @TableField("proctoring_settings")
    private String proctoringSettings;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(Date availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Integer getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Integer shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public Integer getShowAnswers() {
        return showAnswers;
    }

    public void setShowAnswers(Integer showAnswers) {
        this.showAnswers = showAnswers;
    }

    public String getProctoringSettings() {
        return proctoringSettings;
    }

    public void setProctoringSettings(String proctoringSettings) {
        this.proctoringSettings = proctoringSettings;
    }
}