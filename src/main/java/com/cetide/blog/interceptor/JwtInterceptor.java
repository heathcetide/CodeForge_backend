package com.cetide.blog.interceptor;

import com.cetide.blog.common.auth.AuthContext;
import com.cetide.blog.common.security.JwtUtils;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.cetide.blog.exception.AuthorizationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    public JwtInterceptor(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 从请求头中获取token
            String token = request.getHeader("Authorization");
            if (token == null) {
                throw new AuthorizationException("未登录或token已过期");
            }
            System.out.println("第一步token "+ token);
            // 验证token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if (!jwtUtils.validateToken(token)) {
                throw new AuthorizationException("token无效");
            }
            System.out.println("第二步token "+ token);
            AuthContext.setCurrentUser(userService.getUserById(jwtUtils.getUserIdFromToken(token)));
            return true;
        } catch (AuthorizationException e) {
            // 返回401未授权错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
            return false;
        } catch (Exception e) {
            // 返回500服务器内部错误
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":500,\"message\":\"" + e.getMessage() + "\",\"data\":null}");
            return false;
        }
    }

} 