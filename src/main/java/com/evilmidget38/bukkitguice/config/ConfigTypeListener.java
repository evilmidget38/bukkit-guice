package com.evilmidget38.bukkitguice.config;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.lang.reflect.Field;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigTypeListener implements TypeListener {
    private final JavaPlugin plugin;
    private final ConfigManager manager;

    public ConfigTypeListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.manager = new ConfigManager(plugin);
    }

    @Override
    public <I> void hear(TypeLiteral<I> literal, TypeEncounter<I> encounter) {
        Class<?> type = literal.getRawType();
        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() == FileConfiguration.class) {
                    Config annotation = field.getAnnotation(Config.class);
                    if (annotation != null) {
                        encounter.register(new ConfigInjector<I>(field, manager.get(annotation.value(), annotation.copyNewValues())));
                    }
                }
            }
            type = type.getSuperclass();
        }
    }
}
