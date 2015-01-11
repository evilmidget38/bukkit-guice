package com.evilmidget38.bukkitguice.command;

import com.evilmidget38.bukkitguice.ObjectInitializer;
import com.evilmidget38.bukkitguice.PluginModule;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandInitializer implements ObjectInitializer<CommandExecutor> {
    @Override
    public void initialize(JavaPlugin plugin, CommandExecutor commandExecutor) {
        Command command = commandExecutor.getClass().getAnnotation(Command.class);
        PluginCommand pluginCommand = plugin.getCommand(command.value());
        if (pluginCommand == null) {
            plugin.getLogger().severe("Command " + command.value()+ " is not registered in the plugin.yml file, and cannot be registered.");
            return;
        }
        pluginCommand.setExecutor(commandExecutor);
    }

    @Override
    public Class<CommandExecutor> getType() {
        return CommandExecutor.class;
    }
}
