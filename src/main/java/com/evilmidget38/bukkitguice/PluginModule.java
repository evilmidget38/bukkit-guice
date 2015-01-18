package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.command.CommandHandler;
import com.evilmidget38.bukkitguice.config.ConfigProcessor;
import com.evilmidget38.bukkitguice.injectors.TypeListenerBinding;
import com.evilmidget38.bukkitguice.listener.ListenerHandler;
import com.evilmidget38.bukkitguice.plugin.PluginProcessor;
import com.evilmidget38.bukkitguice.scanning.ClassHandler;
import com.evilmidget38.bukkitguice.scanning.JarScanner;
import com.evilmidget38.bukkitguice.services.DefaultServiceManager;
import com.evilmidget38.bukkitguice.services.PluginServiceHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.matcher.Matchers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {
    @Inject
    private JavaPlugin plugin;
    @Inject
    private DefaultServiceManager services;
    @Inject
    private Set<ClassHandler> classHandlers;
    @Inject
    private Set<TypeListenerBinding> typeListeners;

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        // Bind the JavaPlugin instance to its specific class.
        dynamicBind(plugin.getClass()).toInstance(plugin);

        // Bind all type listeners
        for (TypeListenerBinding typeListener : typeListeners) {
            bindListener(typeListener.getMatcher(), typeListener);
        }
        // Class discovery
        JarScanner scanner;
        try {
            scanner = new JarScanner(new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()), plugin.getLogger());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        for (ClassHandler handler : classHandlers) {
            scanner.addHandler(handler);
        }
        try {
            scanner.scan();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Class<?>, Class<?>> entry : services.getBindings().entrySet()) {
            dynamicBind(entry.getKey()).to(entry.getValue());
        }
    }

    // Public and non-generic version of bind.
    public AnnotatedBindingBuilder dynamicBind(Class<?> clazz) {
        return super.bind(clazz);
    }
}
