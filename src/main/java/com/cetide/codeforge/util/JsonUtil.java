package com.cetide.codeforge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public  String convertToJson(String input) {
        // 创建一个 Map 来存储键值对
        Map<String, String> map = new HashMap<>();

        // 使用正则表达式匹配键值对
        Pattern pattern = Pattern.compile("(\\w+):\\s*([\\s\\S]*?)(?=\\n\\w+:|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1).trim();   // 键
            String value = matcher.group(2).trim(); // 值

            // 去除值中的换行符和其他多余空白字符
            value = value.replaceAll("\\s+", " ");
            map.put(key, value);
        }

        try {
            // 禁用格式化输出，生成紧凑的 JSON 字符串
            String key = objectMapper.writeValueAsString(map);
            key.replaceAll("\\\\", " ");
            return key;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
