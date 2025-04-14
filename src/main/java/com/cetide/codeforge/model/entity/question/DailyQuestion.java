package com.cetide.codeforge.model.entity.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日题目记录
 * @TableName daily_question
 */
@TableName(value ="daily_question")
public class DailyQuestion implements Serializable {
    /**
     * 记录 id
     */
    @TableId
    private Long id;

    /**
     * 随机选中的题目 id
     */
    private Long questionId;


    private String questionImg;

    /**
     * 题目对应的日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 记录 id
     */
    public Long getId() {
        return id;
    }

    /**
     * 记录 id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 题目对应的日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 题目对应的日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }
}