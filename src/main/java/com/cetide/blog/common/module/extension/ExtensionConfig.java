package com.cetide.blog.common.module.extension;

import com.cetide.blog.common.module.plugin.ExtensionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtensionConfig {
    @Bean
    public ExtensionRegistry extensionRegistry() {
        return new ExtensionRegistry();
    }
}