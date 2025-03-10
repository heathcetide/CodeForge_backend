package com.cetide.blog.common.module;

import com.cetide.blog.common.module.example.MyPlugin;
import com.cetide.blog.common.module.plugin.PluginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/plugins")
public class PluginController {
    private final PluginManager pluginManager;

    public PluginController(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @PostMapping("/install")
    public ResponseEntity<PluginInfo> installPlugin(@RequestParam("file") MultipartFile pluginFile) {
        return ResponseEntity.ok(pluginManager.installPlugin(pluginFile));
    }

    @DeleteMapping("/{pluginId}")
    public ResponseEntity<Void> uninstallPlugin(@PathVariable String pluginId) {
        pluginManager.uninstallPlugin(pluginId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PluginInfo>> getInstalledPlugins() {
        return ResponseEntity.ok(pluginManager.getInstalledPlugins());
    }

    @PostMapping("/{pluginId}/enable")
    public ResponseEntity<Void> enablePlugin(@PathVariable String pluginId) {
        pluginManager.enablePlugin(pluginId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{pluginId}/disable")
    public ResponseEntity<Void> disablePlugin(@PathVariable String pluginId) {
        pluginManager.disablePlugin(pluginId);
        return ResponseEntity.ok().build();
    }
}