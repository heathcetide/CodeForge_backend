package com.cetide.codeforge.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("tags")
public class Tag extends BaseEntity{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}