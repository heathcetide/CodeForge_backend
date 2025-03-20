package com.cetide.blog.common.security.csrf;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * CSRF 拦截器，用于校验非安全方法请求中的 CSRF Token，
 * 并在响应中添加新的 Token。
 */
@Component
public class CsrfInterceptor implements HandlerInterceptor {

    private final CsrfTokenRepository tokenRepository;

    // 定义安全的 HTTP 方法（不需要 CSRF 校验）
    private static final Set<String> SAFE_METHODS = new HashSet<>(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    public CsrfInterceptor(CsrfTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * preHandle 方法在请求处理之前执行。
     * 对于非安全请求（非 GET/HEAD/TRACE/OPTIONS），校验请求头中的 CSRF Token。
     * 如果验证失败，则直接返回 403 错误。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果请求方法需要 CSRF 校验
        if (!SAFE_METHODS.contains(request.getMethod())) {
            String sessionId = request.getSession().getId();
            String token = request.getHeader("X-CSRF-TOKEN");
            if (!tokenRepository.validateToken(sessionId, token)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token 无效");
                return false;
            }
        }
        return true;
    }

    /**
     * postHandle 方法在控制器处理完请求后、视图渲染之前执行。
     * 在这里我们为响应添加一个新的 CSRF Token。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String sessionId = request.getSession().getId();
        String newToken = tokenRepository.generateToken(sessionId);
        response.setHeader("X-CSRF-TOKEN", newToken);
    }

    // afterCompletion 可用于清理资源，这里可以留空
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // no-op
    }
}
