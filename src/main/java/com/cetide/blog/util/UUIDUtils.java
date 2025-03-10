package com.cetide.blog.util;

import com.cetide.blog.model.dto.ArticleWithWeight;
import com.cetide.blog.model.entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author cetide
 * @DATE 2025/2/14
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}