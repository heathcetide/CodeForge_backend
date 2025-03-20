package com.cetide.codeforge.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.cetide.codeforge.util.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomerIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        return IdGenerator.generateId();
    }
}
