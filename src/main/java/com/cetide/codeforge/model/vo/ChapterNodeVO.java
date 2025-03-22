package com.cetide.codeforge.model.vo;


import com.cetide.codeforge.model.entity.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterNodeVO {
    private Long id;
    private String title;
    private List<ChapterNodeVO> children = new ArrayList<>();

    public ChapterNodeVO(Chapter ch) {
        this.id = ch.getId();
        this.title = ch.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChapterNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterNodeVO> children) {
        this.children = children;
    }
}
