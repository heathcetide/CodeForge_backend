package com.cetide.codeforge.common.aspect;

import com.cetide.codeforge.common.anno.RateLimit;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.common.ratelimit.RedisRateLimiter;
import com.cetide.codeforge.model.enums.RateLimitType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 频率限制切面
 */
@Aspect
@Component
public class RateLimitAspect {

    private final RedisRateLimiter rateLimiter;

    public RateLimitAspect(RedisRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Around("@annotation(com.cetide.codeforge.common.anno.RateLimit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
            
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        String key = generateKey(rateLimit.type(), request, method);
        rateLimiter.checkRateLimit(key, rateLimit.limit(), rateLimit.period());
        
        return point.proceed();
    }
    
    /**
     * 生成限制键
     */
    private String generateKey(RateLimitType type, HttpServletRequest request, Method method) {
        String key;
        switch (type) {
            case IP:
                key = request.getRemoteAddr();
                break;
            case USER:
                key = String.valueOf(AuthContext.getCurrentUser().getId());
                break;
            case INTERFACE:
                key = method.getDeclaringClass().getName() + "." + method.getName();
                break;
            default:
                key = request.getRemoteAddr();
        }
        return key;
    }
} 