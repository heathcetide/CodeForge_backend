package com.cetide.blog.service;

import com.cetide.blog.model.entity.user.PasswordHistory;
import com.cetide.blog.model.entity.user.User;

public interface PasswordService {

    void validateAndUpdatePassword(User user, String newPassword);

    PasswordHistory getPasswordHistoryByUserId(Long userId);

    void savePasswordHistory(PasswordHistory passwordHistory);
}
