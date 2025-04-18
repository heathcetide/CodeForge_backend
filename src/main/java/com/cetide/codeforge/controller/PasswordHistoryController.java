package com.cetide.codeforge.controller;

import com.cetide.codeforge.model.entity.user.PasswordHistory;

import com.cetide.codeforge.service.PasswordService;
import com.cetide.codeforge.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-histories")
public class PasswordHistoryController {

    @Autowired
    private PasswordService passwordHistoryService;

    @PostMapping
    public ApiResponse<PasswordHistory> createPasswordHistory(@RequestBody PasswordHistory passwordHistory) {
        passwordHistoryService.savePasswordHistory(passwordHistory);
        return ApiResponse.success(passwordHistory);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<PasswordHistory> getPasswordHistoryByUserId(@PathVariable Long userId) {
        PasswordHistory passwordHistory = passwordHistoryService.getPasswordHistoryByUserId(userId);
        if (passwordHistory != null) {
            return ApiResponse.success(passwordHistory);
        }
        return ApiResponse.error(404, "Password history not found");
    }
}
