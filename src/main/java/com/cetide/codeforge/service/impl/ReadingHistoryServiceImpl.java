package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.ReadingHistoryMapper;
import com.cetide.codeforge.model.entity.user.ReadingHistory;
import com.cetide.codeforge.service.ReadingHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReadingHistoryServiceImpl extends ServiceImpl<ReadingHistoryMapper, ReadingHistory> implements ReadingHistoryService {

    @Override
    public List<Map<String, Object>> getPopularArticles(int topN) {
        return baseMapper.selectMaps(new QueryWrapper<ReadingHistory>()
                .select("article_id, COUNT(*) AS reader_count")
                .groupBy("article_id")
                .orderByDesc("reader_count")
                .last("LIMIT " + topN));
    }
} 