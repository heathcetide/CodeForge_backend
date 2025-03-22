package com.cetide.codeforge.interceptor;

import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.exception.AuthorizationException;
import com.cetide.codeforge.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AnonymousInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    public AnonymousInterceptor(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        if (token != null) {
            // 验证token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if (!jwtUtils.validateToken(token)) {
                throw new AuthorizationException("token无效");
            }
            AuthContext.setCurrentUser(userService.getUserById(jwtUtils.getUserIdFromToken(token)));
        }
        return true;
    }
}