package com.evilmidget38.bukkitguice.config;

import com.evilmidget38.bukkitguice.injectors.AnnotationProcessor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigProcessor extends AnnotationProcessor<Config> {
    private final JavaPlugin plugin;
    private final ConfigManager manager;

    public ConfigProcessor(JavaPlugin plugin) {
        super(Config.class);
        this.plugin = plugin;
        this.manager = new ConfigManager(plugin);
    }

    @Override
    public Object get(Config annotation) {
        return manager.get(annotation.value(), annotation.copyNewValues());
    }
}
