package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.NotificationMapper;
import com.cetide.codeforge.model.entity.notification.Notification;
import com.cetide.codeforge.service.NotificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public void sendNotification(Notification notification) {
        notificationMapper.insert(notification);
    }
}
