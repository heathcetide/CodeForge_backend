package com.cetide.blog.controller;

import com.cetide.blog.common.auth.AuthContext;
import com.cetide.blog.model.entity.ArticleActivityRecord;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.service.ArticleActivityRecordService;
import com.cetide.blog.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/article-activity")
public class ArticleActivityRecordController {

    @Resource
    private ArticleActivityRecordService service;

    // 获取指定年月的所有记录
    @GetMapping("/{year}/{month}")
    public ApiResponse<List<ArticleActivityRecord>> getActivityDataByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        User user = AuthContext.getCurrentUser();
        QueryWrapper<ArticleActivityRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", year).eq("month", month).eq("user_id", user.getId());
        return ApiResponse.success(service.list(queryWrapper));
    }

    // 获取指定日期的记录
    @GetMapping("/{year}/{month}/{day}")
    public ApiResponse<ArticleActivityRecord> getActivityDataByDate(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        QueryWrapper<ArticleActivityRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", year).eq("month", month).eq("day", day);
        return ApiResponse.success(service.getOne(queryWrapper));
    }

    // 添加创作记录
    @PostMapping
    public ApiResponse<Boolean> addActivityData(@RequestBody ArticleActivityRecord record) {
        return ApiResponse.success(service.save(record));
    }

    // 更新创作记录
    @PutMapping
    public ApiResponse<Boolean> updateActivityData(@RequestBody ArticleActivityRecord record) {
        return ApiResponse.success(service.updateById(record));
    }
}