package com.cetide.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.blog.model.entity.notification.Notification;

public interface NotificationService extends IService<Notification> {
    void sendNotification(Notification notification);
}
