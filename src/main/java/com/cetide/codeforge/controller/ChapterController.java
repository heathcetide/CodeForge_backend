package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.model.entity.Chapter;
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
    @ApiOperation("获取下一章节及其子章节")
    public ApiResponse<List<Chapter>> getNextChapter(@PathVariable Long courseId, @PathVariable Long currentChapterId) {
        // 查询当前章节及其子章节
        return ApiResponse.success(chapterService.getNextChapter(courseId, currentChapterId));
    }
}
