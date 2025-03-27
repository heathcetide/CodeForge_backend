package com.cetide.codeforge.plugin.core;

import com.cetide.codeforge.plugin.Plugin;
import com.cetide.codeforge.plugin.enums.PluginState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PluginLifecycleManager {
    private final ApplicationEventPublisher eventPublisher;

    public PluginLifecycleManager(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void fireInstallEvent(String pluginId) {
        eventPublisher.publishEvent(new PluginEvent(pluginId, "INSTALL"));
    }

    public void fireUninstallEvent(String pluginId) {
        eventPublisher.publishEvent(new PluginEvent(pluginId, "UNINSTALL"));
    }

    public void initializePlugin(Plugin plugin, Map<String, Object> config) {
        // 初始化插件的生命周期，例如加载配置，启动插件等
        plugin.setState(PluginState.ACTIVE);
        // 其他初始化逻辑
    }

    public void stopPlugin(String pluginId) {
        // Implement logic to stop the plugin, such as setting state to INACTIVE or releasing resources
        System.out.println("Stopping plugin with ID: " + pluginId);
        // You can set the state of the plugin to INACTIVE or perform other actions
    }


    public static class PluginEvent {
        private final String pluginId;
        private final String eventType;

        public PluginEvent(String pluginId, String eventType) {
            this.pluginId = pluginId;
            this.eventType = eventType;
        }
    }
}