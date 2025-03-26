package com.cetide.codeforge.config;


import com.cetide.codeforge.interceptor.AdminInterceptor;
import com.cetide.codeforge.interceptor.AnonymousInterceptor;
import com.cetide.codeforge.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final JwtInterceptor jwtInterceptor;

    private final AnonymousInterceptor anonymousInterceptor;

    private final AdminInterceptor adminInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor, AnonymousInterceptor anonymousInterceptor, AdminInterceptor adminInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.anonymousInterceptor = anonymousInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/articles/**").excludePathPatterns("/api/articles/full-search","/api/articles/uploads/**","/api/articles/views/**","/api/articles/get/page","/api/articles/recommend","/api/articles/recommendById")
                .addPathPatterns("/api/comments/**").excludePathPatterns("/api/comments/get/page/**")
                .addPathPatterns("/api/like/**")
                .addPathPatterns("/api/article-activity/**")
                .addPathPatterns("/api/user-course/**")
                .addPathPatterns("/api/courses/add/course/**")
                .addPathPatterns("/api/chapters/**")
                .addPathPatterns("/api/users/info","/api/users/update","/api/users/update","/api/sql-runner/**");
        registry.addInterceptor(anonymousInterceptor)
                .addPathPatterns("/api/courses/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/users/info");
   }

    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("CodeForge API 文档")  // 标题
                .description("CodeForge 是一个支持博客、编程学习、插件化服务、在线课程的综合性学习平台。")  // 描述
                .version("1.0.0")  // 版本号
                .termsOfServiceUrl("https://www.codeforge.com/terms")  // 服务条款 URL
                .contact(new Contact("CodeForge 开发团队", "https://www.codeforge.com", "support@codeforge.com"))  // 联系信息
                .license("Apache 2.0")  // 许可证
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")  // 许可证 URL
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //指定生成接口需要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.cetide.codeforge.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}