package com.cetide.blog.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.cetide.blog.util.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomerIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        return IdGenerator.generateId();
    }
}
