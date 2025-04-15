package com.cetide.codeforge.model.dto.questionSet;


import com.cetide.codeforge.util.PageRequest;

import java.io.Serializable;
import java.util.List;

public class QuestionSetQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private String name;

    private String description;
    /**
     * 标签列表
     */
    private String tags;

    /**
     * 创建用户 id
     */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
