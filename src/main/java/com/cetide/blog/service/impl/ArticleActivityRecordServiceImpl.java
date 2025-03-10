package com.cetide.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.blog.mapper.ArticleActivityRecordMapper;
import com.cetide.blog.model.entity.ArticleActivityRecord;
import com.cetide.blog.service.ArticleActivityRecordService;
import org.springframework.stereotype.Service;

@Service
public class ArticleActivityRecordServiceImpl extends ServiceImpl<ArticleActivityRecordMapper, ArticleActivityRecord> implements ArticleActivityRecordService {

}