package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.Chapter;

import java.util.List;

public interface ChapterService extends IService<Chapter> {

    /**
     * 获取下一个章节
     */
    List<Chapter> getNextChapter(Long courseId, Long currentChapterId);
}
