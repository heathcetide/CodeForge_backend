package com.cetide.blog.config;


import com.cetide.blog.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import javax.annotation.Resource;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/articles/**").excludePathPatterns("/api/articles/full-search","/api/articles/uploads/**","/api/articles/views/**","/api/articles/get/page","/api/articles/recommend","/api/articles/recommendById")
                .addPathPatterns("/api/comments/**").excludePathPatterns("/api/comments/get/page/**")
                .addPathPatterns("/api/article-activity/**");
   }

}