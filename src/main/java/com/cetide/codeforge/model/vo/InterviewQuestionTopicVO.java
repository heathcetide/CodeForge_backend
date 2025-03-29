package com.cetide.codeforge.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 专题图片
 *
 * @author heathcetide
 */
public class InterviewQuestionTopicVO {

    /**
     * 专题id
     */
    @ApiModelProperty(value = "专题id")
    private Long id;

    /**
     * 专题图片
     */
    @ApiModelProperty(value = "专题图片")
    private String coverImage;


    /**
     * 专题名称: 专题名称
     */
    @ApiModelProperty(value = "专题名称: 专题名称")
    private String topicName;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
