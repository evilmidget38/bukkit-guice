package com.evilmidget38.bukkitguice.listener;

import com.evilmidget38.bukkitguice.ObjectInitializer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerInitializer implements ObjectInitializer<Listener> {
    @Override
    public void initialize(JavaPlugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public Class<Listener> getType() {
        return Listener.class;
    }
}
