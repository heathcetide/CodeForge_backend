package com.cetide.blog.service;

public interface EmailService {

    void sendWelcomeEmail(String username, String email) throws Exception;


    void sendEmailWithRetry(String email, String code, int maxRetries, long retryInterval) throws Exception;
}
