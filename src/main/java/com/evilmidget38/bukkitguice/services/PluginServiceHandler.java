package com.evilmidget38.bukkitguice.services;

import com.evilmidget38.bukkitguice.scanning.ClassHandlerAdapter;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import java.util.Set;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class PluginServiceHandler extends ClassHandlerAdapter {
    private static final Set<Class<?>> IGNORE = ImmutableSet.of(Listener.class, CommandExecutor.class);
    @Inject
    private ServiceManager serviceManager;


    @Override
    @SuppressWarnings("unchecked")
    public void handle(Class<?> clazz) {
        PluginService service = clazz.getAnnotation(PluginService.class);
        if (service != null) {
            Class<?> serviceType = getServiceType(service, clazz);
            serviceManager.registerClass(serviceType, clazz);
        }
    }

    private static Class<?> getServiceType(PluginService service, Class<?> clazz) {
        Class<?> serviceType = service.value();
        if (serviceType == PluginService.class) {
            Class[] interfaces = clazz.getInterfaces();
            if (interfaces.length == 1 && !IGNORE.contains(interfaces[0])) {
                serviceType = interfaces[0];
            } else {
                serviceType = clazz;
            }
        }
        return serviceType;
    }
}
