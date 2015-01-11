package com.evilmidget38.bukkitguice;

import org.bukkit.plugin.java.JavaPlugin;

public interface ObjectInitializer<T> {
    public void initialize(JavaPlugin plugin, T t);
    public Class<T> getType();
}
