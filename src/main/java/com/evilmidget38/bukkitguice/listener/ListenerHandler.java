package com.evilmidget38.bukkitguice.listener;

import com.evilmidget38.bukkitguice.scanning.ClassHandlerAdapter;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Set;
import org.bukkit.event.Listener;

public class ListenerHandler extends ClassHandlerAdapter {

    @Inject
    @Named("discovered")
    private Set<Class<?>> discovered;

    @Override
    public void handle(Class<?> clazz) {
        if (Listener.class.isAssignableFrom(clazz)) {
            discovered.add(clazz);
        }
    }
}
