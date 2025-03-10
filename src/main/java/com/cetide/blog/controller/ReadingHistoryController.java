package com.cetide.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.blog.model.entity.user.ReadingHistory;
import com.cetide.blog.service.ReadingHistoryService;
import com.cetide.blog.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reading-histories")
public class ReadingHistoryController {
    @Autowired
    private ReadingHistoryService readingHistoryService;

    @PostMapping
    public ApiResponse<ReadingHistory> recordReading(@RequestBody ReadingHistory history) {
        readingHistoryService.save(history);
        return ApiResponse.success(history);
    }

    @GetMapping("/user/{userId}")
    public List<ReadingHistory> getUserHistory(@PathVariable Long userId) {
        return readingHistoryService.lambdaQuery()
                .eq(ReadingHistory::getUserId, userId)
                .list();
    }

    @Operation(summary = "分页查询阅读记录")
    @GetMapping
    public ApiResponse<Page<ReadingHistory>> getHistoryByPage(
        @Parameter(description = "用户ID") @PathVariable Long userId,
        @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
        @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        
        return ApiResponse.success(readingHistoryService.lambdaQuery()
            .eq(ReadingHistory::getUserId, userId)
            .orderByDesc(ReadingHistory::getReadAt)
            .page(new Page<>(page, size)));
    }

    @Operation(summary = "获取最近阅读统计")
    @GetMapping("/stats/{userId}")
    public ApiResponse<Map<String, Object>> getReadStats(
        @Parameter(description = "用户ID") @PathVariable Long userId) {
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRead", readingHistoryService.count(
            new QueryWrapper<ReadingHistory>().eq("user_id", userId)));
        stats.put("weeklyRead", readingHistoryService.lambdaQuery()
            .eq(ReadingHistory::getUserId, userId)
            .ge(ReadingHistory::getReadAt, LocalDateTime.now().minusWeeks(1))
            .count());
        return ApiResponse.success(stats);
    }

    @Operation(summary = "获取某篇文章的阅读人数")
    @GetMapping("/article/{articleId}/readers")
    public ApiResponse<Long> getArticleReaderCount(@PathVariable Long articleId) {
        long readerCount = readingHistoryService.lambdaQuery()
                .eq(ReadingHistory::getArticleId, articleId)
                .count();
        return ApiResponse.success(readerCount);
    }

    @Operation(summary = "获取用户对某篇文章的阅读状态")
    @GetMapping("/user/{userId}/article/{articleId}/status")
    public ApiResponse<String> getReadingStatusForArticle(
            @PathVariable Long userId, @PathVariable Long articleId) {

        ReadingHistory history = readingHistoryService.lambdaQuery()
                .eq(ReadingHistory::getUserId, userId)
                .eq(ReadingHistory::getArticleId, articleId)
                .one();

        if (history != null) {
            return ApiResponse.success(history.getStatus());
        } else {
            return ApiResponse.error(404, "阅读记录不存在");
        }
    }

    @Operation(summary = "删除阅读记录")
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteReadingHistory(@PathVariable Long id) {
        boolean isDeleted = readingHistoryService.removeById(id);
        return ApiResponse.success(isDeleted);
    }

    @Operation(summary = "更新阅读记录")
    @PutMapping("/{id}")
    public ApiResponse<ReadingHistory> updateReadingHistory(
            @PathVariable Long id, @RequestBody ReadingHistory history) {

        history.setId(id);
        boolean isUpdated = readingHistoryService.updateById(history);

        if (isUpdated) {
            return ApiResponse.success(history);
        } else {
            return ApiResponse.error(500, "更新失败");
        }
    }

    @Operation(summary = "按状态统计阅读记录")
    @GetMapping("/stats/status/{userId}")
    public ApiResponse<Map<String, Object>> getReadingStatsByStatus(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "状态") @RequestParam String status) {

        Map<String, Object> stats = new HashMap<>();
        stats.put("statusRead", readingHistoryService.lambdaQuery()
                .eq(ReadingHistory::getUserId, userId)
                .eq(ReadingHistory::getStatus, status)
                .count());
        return ApiResponse.success(stats);
    }

    @Operation(summary = "获取某个时间段内的阅读记录")
    @GetMapping("/user/{userId}/range")
    public ApiResponse<Page<ReadingHistory>> getReadingHistoryByDateRange(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "开始日期") @RequestParam LocalDateTime startDate,
            @Parameter(description = "结束日期") @RequestParam LocalDateTime endDate,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {

        QueryWrapper<ReadingHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .ge("read_at", startDate)
                .le("read_at", endDate);

        return ApiResponse.success(readingHistoryService.page(new Page<>(page, size), queryWrapper));
    }

    @Operation(summary = "获取用户阅读历史中的最久阅读记录")
    @GetMapping("/user/{userId}/longest")
    public ApiResponse<ReadingHistory> getLongestReadingHistory(
            @Parameter(description = "用户ID") @PathVariable Long userId) {

        ReadingHistory longestReadingHistory = readingHistoryService.lambdaQuery()
                .eq(ReadingHistory::getUserId, userId)
                .orderByDesc(ReadingHistory::getReadingTime)
                .last("LIMIT 1")
                .one();

        if (longestReadingHistory != null) {
            return ApiResponse.success(longestReadingHistory);
        } else {
            return ApiResponse.error(404, "没有找到阅读记录");
        }
    }

    @Operation(summary = "批量更新阅读记录")
    @PutMapping("/batch")
    public ApiResponse<Boolean> updateReadingHistoryBatch(
            @RequestBody List<ReadingHistory> histories) {

        boolean isUpdated = readingHistoryService.updateBatchById(histories);
        if (isUpdated) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error(500, "批量更新失败");
        }
    }

    @Operation(summary = "获取用户对某篇文章的阅读时长")
    @GetMapping("/user/{userId}/article/{articleId}/time")
    public ApiResponse<Integer> getReadingTimeForArticle(
            @PathVariable Long userId,
            @PathVariable Long articleId) {

        Integer totalReadingTime = readingHistoryService.getBaseMapper()
                .selectObjs(new QueryWrapper<ReadingHistory>()
                        .eq("user_id", userId)
                        .eq("article_id", articleId)
                        .select("SUM(reading_time) AS total_time"))
                .stream()
                .map(obj -> (Integer) obj)
                .findFirst()
                .orElse(0);

        return ApiResponse.success(totalReadingTime);
    }

    @Operation(summary = "获取用户所有阅读状态的分布")
    @GetMapping("/user/{userId}/status-distribution")
    public ApiResponse<Map<String, Long>> getReadingStatusDistribution(
            @PathVariable Long userId) {

        Map<String, Long> statusDistribution = new HashMap<>();
        for (String status : new String[]{"read", "unread", "in_progress"}) {
            Long count = readingHistoryService.lambdaQuery()
                    .eq(ReadingHistory::getUserId, userId)
                    .eq(ReadingHistory::getStatus, status)
                    .count();
            statusDistribution.put(status, count);
        }

        return ApiResponse.success(statusDistribution);
    }

    @Operation(summary = "获取最受欢迎的文章")
    @GetMapping("/popular-articles")
    public ApiResponse<List<Map<String, Object>>> getPopularArticles(
            @RequestParam(defaultValue = "10") int topN) {

        List<Map<String, Object>> popularArticles = readingHistoryService.getPopularArticles(topN);
        return ApiResponse.success(popularArticles);
    }

    @Operation(summary = "获取所有文章的阅读时长总和")
    @GetMapping("/article/{articleId}/total-time")
    public ApiResponse<Integer> getTotalReadingTimeForArticle(@PathVariable Long articleId) {
        Integer totalReadingTime = readingHistoryService.getBaseMapper()
                .selectObjs(new QueryWrapper<ReadingHistory>()
                        .eq("article_id", articleId)
                        .select("SUM(reading_time) AS total_time"))
                .stream()
                .map(obj -> (Integer) obj)
                .findFirst()
                .orElse(0);

        return ApiResponse.success(totalReadingTime);
    }

} 