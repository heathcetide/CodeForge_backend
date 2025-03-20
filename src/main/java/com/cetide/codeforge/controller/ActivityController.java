package com.cetide.codeforge.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.model.entity.Activity;
import com.cetide.codeforge.service.ActivityService;
import com.cetide.codeforge.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ApiResponse<Activity> createActivity(@RequestBody Activity activity) {
        activityService.save(activity);
        return ApiResponse.success(activity);
    }

    @GetMapping("/{id}")
    public ApiResponse<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getById(id);
        if (activity != null) {
            return ApiResponse.success(activity);
        }
        return ApiResponse.error(404, "Activity not found");
    }

    @PutMapping("/{id}")
    public ApiResponse<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id);
        activityService.updateById(activity);
        return ApiResponse.success(activity);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteActivity(@PathVariable Long id) {
        boolean result = activityService.removeById(id);
        return ApiResponse.success(result);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<Page<Activity>> getActivitiesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Activity> activityPage = activityService.lambdaQuery()
                .eq(Activity::getUserId, userId)
                .orderByDesc(Activity::getCreatedAt)
                .page(new Page<>(page, size));

        return ApiResponse.success(activityPage);
    }

    @GetMapping("/type/{activityType}")
    public ApiResponse<Page<Activity>> getActivitiesByType(
            @PathVariable String activityType,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Activity> activityPage = activityService.lambdaQuery()
                .eq(Activity::getActivityType, activityType)
                .orderByDesc(Activity::getCreatedAt)
                .page(new Page<>(page, size));

        return ApiResponse.success(activityPage);
    }

    @GetMapping("/recent/{userId}")
    public ApiResponse<Page<Activity>> getRecentActivities(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Activity> activityPage = activityService.lambdaQuery()
                .eq(Activity::getUserId, userId)
                .ge(Activity::getCreatedAt, LocalDateTime.now().minusDays(7))
                .orderByDesc(Activity::getCreatedAt)
                .page(new Page<>(page, size));

        return ApiResponse.success(activityPage);
    }

    @GetMapping("/status/{status}")
    public ApiResponse<Page<Activity>> getActivitiesByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Activity> activityPage = activityService.lambdaQuery()
                .eq(Activity::getStatus, status)
                .orderByDesc(Activity::getCreatedAt)
                .page(new Page<>(page, size));

        return ApiResponse.success(activityPage);
    }

    @DeleteMapping("/batch")
    public ApiResponse<Boolean> deleteActivities(@RequestBody List<Long> ids) {
        boolean result = activityService.removeByIds(ids);
        return ApiResponse.success(result);
    }

    @GetMapping("/stats/{userId}")
    public ApiResponse<Map<String, Object>> getActivityStats(@PathVariable Long userId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalActivities", activityService.count(new QueryWrapper<Activity>().eq("user_id", userId)));
        stats.put("loginActivities", activityService.lambdaQuery().eq(Activity::getUserId, userId).eq(Activity::getActivityType, "login").count());
        stats.put("completedActivities", activityService.lambdaQuery().eq(Activity::getUserId, userId).eq(Activity::getStatus, "completed").count());
        return ApiResponse.success(stats);
    }

    @GetMapping("/tags/{tag}")
    public ApiResponse<Page<Activity>> getActivitiesByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Activity> activityPage = activityService.lambdaQuery()
                .like(Activity::getTags, tag)
                .orderByDesc(Activity::getCreatedAt)
                .page(new Page<>(page, size));

        return ApiResponse.success(activityPage);
    }

    @PutMapping("/status/{id}")
    public ApiResponse<Activity> updateActivityStatus(@PathVariable Long id, @RequestParam String status) {
        Activity activity = activityService.getById(id);
        if (activity != null) {
            activity.setStatus(status);
            activityService.updateById(activity);
            return ApiResponse.success(activity);
        }
        return ApiResponse.error(404, "Activity not found");
    }
}
