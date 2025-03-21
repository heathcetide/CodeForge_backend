package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.ChapterMapper;
import com.cetide.codeforge.model.entity.Chapter;
import com.cetide.codeforge.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    private final ChapterMapper chapterMapper;

    public ChapterServiceImpl(ChapterMapper chapterMapper) {
        this.chapterMapper = chapterMapper;
    }

    @Override
    public List<Chapter> getNextChapter(Long courseId, Long currentChapterId) {
        // 如果当前章节ID为0，说明是查询根章节（课程的第一章）
        if (currentChapterId == 0) {
            return chapterMapper.selectByCourseIdAndParentId(courseId, 0L);  // 传入parent_id=0
        }
        // 获取当前章节的子章节
        List<Chapter> childChapters = chapterMapper.selectByParentId(currentChapterId);
        // 如果当前章节有子章节，则返回这些子章节
        if (!childChapters.isEmpty()) {
            return childChapters;
        }
        // 如果没有子章节，返回下一个章节
        return chapterMapper.selectByCourseIdAndOrder(courseId, currentChapterId);
    }
}
