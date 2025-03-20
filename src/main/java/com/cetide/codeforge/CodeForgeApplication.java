package com.cetide.codeforge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.cetide.codeforge.mapper")
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class CodeForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeForgeApplication.class, args);
    }
} 