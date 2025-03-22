package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.mapper.ChapterMapper;
import com.cetide.codeforge.model.entity.Chapter;
import com.cetide.codeforge.model.vo.ChapterNodeVO;
import com.cetide.codeforge.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    private final ChapterMapper chapterMapper;

    public ChapterServiceImpl(ChapterMapper chapterMapper) {
        this.chapterMapper = chapterMapper;
    }

    @Override
    public Chapter getNextChapter(Long courseId, Long currentChapterId) {
        // 如果当前章节为0，返回课程第一个根章节
        if (currentChapterId == 0) {
            List<Chapter> rootChapters = chapterMapper.selectByCourseIdAndParentId(courseId, 0L);
            return rootChapters.isEmpty() ? null : rootChapters.get(0);
        }

        // 查找当前章节的子章节（如果有，返回第一个）
        List<Chapter> children = chapterMapper.selectByParentId(currentChapterId);
        if (!children.isEmpty()) {
            return children.get(0);
        }
        Chapter chapter = chapterMapper.selectNextChapterGlobally(courseId, currentChapterId);
        if (chapter != null) {
            return chapter;
        }
        throw new ResourceNotFoundException("已经是最后一章了");
    }

    @Override
    public Chapter getChapterDetail(Long courseId, Long chapterId) {
        return chapterMapper.selectByCourseIdAndChapterId(courseId, chapterId);
    }

    @Override
    public List<ChapterNodeVO> getChapterTree(Long courseId) {
        List<Chapter> allChapters = chapterMapper.selectByCourseId(courseId);

        // 先找出所有一级章节（parent_id = 0）
        List<ChapterNodeVO> tree = allChapters.stream()
                .filter(ch -> ch.getParentId() == 0)
                .map(ch -> {
                    ChapterNodeVO node = new ChapterNodeVO(ch);
                    List<ChapterNodeVO> children = allChapters.stream()
                            .filter(sub -> Objects.equals(sub.getParentId(), ch.getId()))
                            .map(ChapterNodeVO::new)
                            .collect(Collectors.toList());
                    node.setChildren(children);
                    return node;
                })
                .collect(Collectors.toList());

        return tree;
    }
}
