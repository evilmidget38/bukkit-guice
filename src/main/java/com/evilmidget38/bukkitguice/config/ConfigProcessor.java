package com.evilmidget38.bukkitguice.config;

import com.evilmidget38.bukkitguice.injectors.AnnotationProcessor;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigProcessor extends AnnotationProcessor<Config> {
    private final JavaPlugin plugin;
    private final ConfigManager manager;

    @Inject
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
