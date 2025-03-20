package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.FollowerMapper;
import com.cetide.codeforge.model.entity.Follower;
import com.cetide.codeforge.service.FollowerService;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower> implements FollowerService {
    // 可以在这里实现自定义的业务逻辑
}