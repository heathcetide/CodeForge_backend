package com.cetide.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.blog.model.entity.notification.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
