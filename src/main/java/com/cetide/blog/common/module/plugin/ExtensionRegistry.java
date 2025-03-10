package com.cetide.blog.common.module.plugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 扩展点注册中心
 */
public class ExtensionRegistry {
    // 使用线程安全的Map存储扩展点，Key为扩展点接口类型
    private final Map<Class<?>, List<Object>> extensions = new ConcurrentHashMap<>();
    /**
     * 注册扩展实现
     * @param extensionType 扩展点接口类型
     * @param extension 扩展实现实例
     */
    public <T> void registerExtension(Class<T> extensionType, T extension) {
        extensions.computeIfAbsent(extensionType, k -> new CopyOnWriteArrayList<>())
                .add(extension);
    }
    /**
     * 获取指定类型的所有扩展实现
     * @param extensionType 扩展点接口类型
     * @return 该类型的所有扩展实现
     */
    public <T> List<T> getExtensions(Class<T> extensionType) {
        return extensions.getOrDefault(extensionType, Collections.emptyList())
                .stream()
                .map(extensionType::cast)
                .collect(Collectors.toList());
    }
    /**
     * 清除所有注册的扩展
     */
    public void clear() {
        extensions.clear();
    }
}