package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.notification.Notification;

public interface NotificationService extends IService<Notification> {
    void sendNotification(Notification notification);
}
