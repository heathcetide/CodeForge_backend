package com.cetide.codeforge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(10);
        // 队列大小
        executor.setQueueCapacity(25);
        // 线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称前缀
        executor.setThreadNamePrefix("Async-");
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}
