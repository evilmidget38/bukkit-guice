package com.evilmidget38.bukkitguice.command;

import com.evilmidget38.bukkitguice.PluginModule;
import com.evilmidget38.bukkitguice.scanning.ClassHandlerAdapter;
import com.evilmidget38.bukkitguice.services.PluginService;
import org.bukkit.command.CommandExecutor;

public class CommandHandler  extends ClassHandlerAdapter {
    private final PluginModule pluginModule;

    public CommandHandler(PluginModule pluginModule) {
        this.pluginModule = pluginModule;
    }

    @Override
    public void handle(Class<?> clazz) {
        Command command = clazz.getAnnotation(Command.class);
        if (CommandExecutor.class.isAssignableFrom(clazz) && command != null) {
            pluginModule.getDiscovered().add(clazz);
        }
    }
}
