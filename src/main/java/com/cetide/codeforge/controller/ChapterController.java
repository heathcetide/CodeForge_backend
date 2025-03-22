package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.model.entity.Chapter;
import com.cetide.codeforge.model.vo.ChapterNodeVO;
import com.cetide.codeforge.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "章节模块")
@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    /**
     * 根据 course_id 获取章节，并根据 currentChapterId 获取下一章节及其子章节
     */
    @GetMapping("/{courseId}/next/{currentChapterId}")
    @ApiOperation("获取下一章节")
    public ApiResponse<Chapter> getNextChapter(@PathVariable Long courseId, @PathVariable Long currentChapterId) {
        return ApiResponse.success(chapterService.getNextChapter(courseId, currentChapterId));
    }

    @GetMapping("/{courseId}/chapter/{chapterId}")
    @ApiOperation("获取当前章节详情")
    public ApiResponse<Chapter> getChapterDetail(@PathVariable Long courseId, @PathVariable Long chapterId) {
        return ApiResponse.success(chapterService.getChapterDetail(courseId, chapterId));
    }

    @GetMapping("/{courseId}/directory")
    @ApiOperation("获取课程章节目录（树状结构）")
    public ApiResponse<List<ChapterNodeVO>> getChapterDirectory(@PathVariable Long courseId) {
        return ApiResponse.success(chapterService.getChapterTree(courseId));
    }
}
