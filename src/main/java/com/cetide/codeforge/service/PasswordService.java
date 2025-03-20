package com.cetide.codeforge.service;

import com.cetide.codeforge.model.entity.user.PasswordHistory;
import com.cetide.codeforge.model.entity.user.User;

public interface PasswordService {

    void validateAndUpdatePassword(User user, String newPassword);

    PasswordHistory getPasswordHistoryByUserId(Long userId);

    void savePasswordHistory(PasswordHistory passwordHistory);
}
