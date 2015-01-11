package com.evilmidget38.bukkitguice.config;

import com.google.inject.MembersInjector;
import java.lang.reflect.Field;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final FileConfiguration toInject;

    public ConfigInjector(Field field, FileConfiguration toInject) {
        this.field = field;
        this.toInject = toInject;
    }

    @Override
    public void injectMembers(T t) {
        try {
            field.setAccessible(true);
            field.set(t, toInject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
