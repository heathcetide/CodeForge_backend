package com.cetide.blog.service.impl;

import com.cetide.blog.exception.PasswordPolicyException;
import com.cetide.blog.mapper.PasswordHistoryMapper;
import com.cetide.blog.model.entity.user.PasswordHistory;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.service.PasswordService;
import com.cetide.blog.service.UserService;
import com.cetide.blog.util.BcryptUtil;
import com.mysql.cj.exceptions.PasswordExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {
    
    private final UserService userService;
    private final PasswordHistoryMapper passwordHistoryMapper;

    @Value("${security.password.min-length}") private int minLength;
    @Value("${security.password.require-upper}") private boolean requireUpper;
    @Value("${security.password.require-lower}") private boolean requireLower;
    @Value("${security.password.require-digit}") private boolean requireDigit;
    @Value("${security.password.require-special}") private boolean requireSpecial;
    @Value("${security.password.history-size}") private int historySize;
    @Value("${security.password.max-age-days}") private int maxAgeDays;

    public PasswordServiceImpl(
        UserService userService, 
        PasswordHistoryMapper passwordHistoryMapper
    ) {
        this.userService = userService;
        this.passwordHistoryMapper = passwordHistoryMapper;
    }

    @Override
    @Transactional
    public void validateAndUpdatePassword(User user, String newPassword) {
        validatePasswordComplexity(newPassword);
        checkPasswordHistory(user, newPassword);
        checkPasswordAge(user);
        
        // 记录历史密码
        PasswordHistory history = new PasswordHistory();
        history.setUserId(user.getId());
        history.setPasswordHash(user.getPasswordHash());
        history.setCreatedAt(LocalDateTime.now());
        passwordHistoryMapper.insert(history);
        
        // 更新密码
        user.setPasswordHash(BcryptUtil.encode(newPassword));
        user.setPasswordUpdatedAt(new Date());
        userService.updateById(user);
    }

    private void validatePasswordComplexity(String password) {
        if (password.length() < minLength) {
            throw new PasswordPolicyException("密码长度至少需要" + minLength + "位");
        }
        if (requireUpper && !password.matches(".*[A-Z].*")) {
            throw new PasswordPolicyException("密码必须包含大写字母");
        }
        if (requireLower && !password.matches(".*[a-z].*")) {
            throw new PasswordPolicyException("密码必须包含小写字母");
        }
        if (requireDigit && !password.matches(".*\\d.*")) {
            throw new PasswordPolicyException("密码必须包含数字");
        }
        if (requireSpecial && !password.matches(".*[!@#$%^&*].*")) {
            throw new PasswordPolicyException("密码必须包含特殊字符");
        }
    }

    private void checkPasswordHistory(User user, String newPassword) {
//        List<String> recentPasswords = userService.getPasswordHistory(user.getId(), historySize);
//        if (recentPasswords.stream().anyMatch(old -> BcryptUtil.matches(newPassword, old))) {
//            throw new PasswordPolicyException("不能使用最近" + historySize + "次使用过的密码");
//        }
    }

    private void checkPasswordAge(User user) {
        if (maxAgeDays > 0 && user.getPasswordUpdatedAt() != null) {
            long daysSinceLastChange = user.getPasswordUpdatedAt().getTime() -  new Date().getTime();
            if (daysSinceLastChange >= maxAgeDays) {
                throw new PasswordExpiredException("密码已过期，请修改密码");
            }
        }
    }

    @Override
    public PasswordHistory getPasswordHistoryByUserId(Long userId) {
        return passwordHistoryMapper.selectById(userId);
    }

    @Override
    public void savePasswordHistory(PasswordHistory passwordHistory) {
        passwordHistoryMapper.insert(passwordHistory);  // 插入新的密码记录
    }

} 