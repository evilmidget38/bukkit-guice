package com.evilmidget38.bukkitguice.command;

import com.evilmidget38.bukkitguice.scanning.ClassHandlerAdapter;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Set;
import org.bukkit.command.CommandExecutor;

public class CommandHandler  extends ClassHandlerAdapter {

    @Inject
    @Named("discovered")
    private Set<Class<?>> discovered;

    @Override
    public void handle(Class<?> clazz) {
        Command command = clazz.getAnnotation(Command.class);
        if (CommandExecutor.class.isAssignableFrom(clazz) && command != null) {
            discovered.add(clazz);
        }
    }
}
