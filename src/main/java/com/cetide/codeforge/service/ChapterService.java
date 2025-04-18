package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.Chapter;
import com.cetide.codeforge.model.vo.ChapterNodeVO;

import java.util.List;

public interface ChapterService extends IService<Chapter> {

    /**
     * 获取下一个章节
     */
    Chapter getNextChapter(Long courseId, Long currentChapterId);

    Chapter getChapterDetail(Long courseId, Long chapterId);

    List<ChapterNodeVO> getChapterTree(Long courseId);

    Long getFirstChapterByCourseId(String id);
}
