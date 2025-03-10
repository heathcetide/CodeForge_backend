package com.cetide.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.blog.model.entity.user.ReadingHistory;

import java.util.List;
import java.util.Map;

public interface ReadingHistoryService extends IService<ReadingHistory> {
    List<Map<String, Object>> getPopularArticles(int topN);
} 