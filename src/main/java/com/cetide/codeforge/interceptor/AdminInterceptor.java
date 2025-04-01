package com.cetide.codeforge.interceptor;

import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.common.encrypt.KmsService;
import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.exception.AuthorizationException;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    private final JwtUtils jwtUtils;
    private final UserService userService;
    // 假设你已经初始化好 RSA 私钥
    private final KmsService kmsService;

    public AdminInterceptor(JwtUtils jwtUtils, UserService userService, KmsService kmsService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.kmsService = kmsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            logger.info("AdminInterceptor: {} {}", request.getMethod(), request.getRequestURI());

            // 1. 获取请求头中的 Authorization token
            String token = request.getHeader("Authorization");
            if (token == null || token.trim().isEmpty()) {
                throw new AuthorizationException("未登录或 token 已过期");
            }
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if (!jwtUtils.validateToken(token)) {
                throw new AuthorizationException("token 无效或已过期");
            }
            String decryptedData;
            String AEncryptedData = request.getHeader("A-Encrypted-Data");
            if (AEncryptedData == null || AEncryptedData.trim().isEmpty()) {
                throw new AuthorizationException("token数据缺失");
            } else {
                decryptedData = kmsService.decrypt(AEncryptedData, "AES");
            }
            // 此处可以对解密后的数据进行额外校验，比如时间戳、随机数等
            logger.info("解密后的数据：{}", decryptedData);
            if (!decryptedData.equals(token)) {
                throw new AuthorizationException("token数据有误");
            }
            // 3. 根据 token 获取用户信息
            Long userId = jwtUtils.getUserIdFromToken(token);
            User user = userService.getUserById(userId);
            if (user == null) {
                throw new AuthorizationException("用户不存在");
            }
            if (!user.isAccountNonLocked()) {
                throw new AuthorizationException("账号已经封锁");
            }
            AuthContext.setCurrentUser(user);
            AuthContext.setCurrentToken(token);
            logger.info("AdminInterceptor: 用户 {} 通过权限校验", user.getUsername());
            return true;
        } catch (AuthorizationException e) {
            logger.warn("AuthorizationException: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
            return false;
        } catch (Exception e) {
            logger.error("AdminInterceptor 内部错误", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":500,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
            return false;
        }
    }
}