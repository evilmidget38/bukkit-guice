package com.evilmidget38.bukkitguice.listener;

import com.evilmidget38.bukkitguice.PluginModule;
import com.evilmidget38.bukkitguice.scanning.ClassHandlerAdapter;
import org.bukkit.event.Listener;

public class ListenerHandler extends ClassHandlerAdapter {
    private final PluginModule module;

    public ListenerHandler(PluginModule module) {
        this.module = module;
    }

    @Override
    public void handle(Class<?> clazz) {
        if (Listener.class.isAssignableFrom(clazz)) {
            module.getDiscovered().add(clazz);
        }
    }
}
