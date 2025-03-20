package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.notification.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
