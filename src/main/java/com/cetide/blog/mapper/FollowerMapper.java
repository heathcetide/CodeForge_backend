package com.cetide.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.cetide.blog.model.entity.Follower;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowerMapper extends BaseMapper<Follower> {
    // 可以在这里定义自定义的查询方法
}