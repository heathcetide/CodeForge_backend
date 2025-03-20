package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.user.ReadingHistory;

import java.util.List;
import java.util.Map;

public interface ReadingHistoryService extends IService<ReadingHistory> {
    List<Map<String, Object>> getPopularArticles(int topN);
} 