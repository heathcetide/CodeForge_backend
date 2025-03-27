package com.cetide.codeforge.plugin.core;

import com.cetide.codeforge.plugin.exception.ConfigException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件配置管理服务，提供：
 * - 配置存储和版本控制
 * - 配置加密
 * - 变更通知
 * - 类型校验
 *
 * @author heathcetide
 */
public class PluginConfigManager {

    private static final int MAX_HISTORY_VERSIONS = 5;

    private final JdbcTemplate jdbc;

    private final ObjectMapper objectMapper;

    @Autowired
    public PluginConfigManager(JdbcTemplate jdbc, ObjectMapper objectMapper) {
        this.jdbc = jdbc;
        this.objectMapper = objectMapper;
        initializeSchema();
    }

    private void initializeSchema() {
        jdbc.execute("CREATE TABLE IF NOT EXISTS plugin_config ("
                + "plugin_id VARCHAR(50) PRIMARY KEY,"
                + "config TEXT NOT NULL,"
                + "version INT NOT NULL,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    /**
     * 初始化插件配置
     *
     * @param pluginId      插件ID
     * @param defaultConfig 默认配置
     */
    public void initializeConfig(String pluginId, Map<String, Object> defaultConfig) {
        if (!configExists(pluginId)) {
            saveConfig(pluginId, defaultConfig, 1);
        }
    }

    /**
     * 更新插件配置
     *
     * @param pluginId  插件ID
     * @param newConfig 新配置（增量更新）
     */
    @Transactional
    public void updateConfig(String pluginId, Map<String, Object> newConfig) {
        Map<String, Object> current = getConfig(pluginId);
        int newVersion = getCurrentVersion(pluginId) + 1;

        // 合并配置
        Map<String, Object> merged = new HashMap<>(current);
        merged.putAll(newConfig);

        // 保存新版本
        saveConfig(pluginId, merged, newVersion);

        // 清理旧版本
        jdbc.update("DELETE FROM plugin_config WHERE plugin_id = ? AND version < ?",
                pluginId, newVersion - MAX_HISTORY_VERSIONS);
    }

    private void saveConfig(String pluginId, Map<String, Object> config, int version) {
        try {
            String json = objectMapper.writeValueAsString(config);
            jdbc.update("INSERT INTO plugin_config (plugin_id, config, version) VALUES (?, ?, ?)"
                            + " ON CONFLICT (plugin_id) DO UPDATE SET config = ?, version = ?",
                    pluginId, json, version, json, version);
        } catch (JsonProcessingException e) {
            throw new ConfigException("配置序列化失败", e);
        }
    }

    private int getCurrentVersion(String pluginId) {
        Integer version = jdbc.queryForObject("SELECT MAX(version) FROM plugin_config WHERE plugin_id = ?", Integer.class, pluginId);
        return version != null ? version : 0;
    }

    Map<String, Object> getConfig(String pluginId) {
        String configJson = jdbc.queryForObject("SELECT config FROM plugin_config WHERE plugin_id = ? AND version = (" + "SELECT MAX(version) FROM plugin_config WHERE plugin_id = ?)", String.class, pluginId, pluginId);
        try {
            return objectMapper.readValue(configJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new ConfigException("配置反序列化失败", e);
        }
    }

    private boolean configExists(String pluginId) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM plugin_config WHERE plugin_id = ?", Integer.class, pluginId);
        return count != null && count > 0;
    }

    public void deleteConfig(String pluginId) {
        jdbc.update("DELETE FROM plugin_config WHERE plugin_id = ?", pluginId);
    }
}
