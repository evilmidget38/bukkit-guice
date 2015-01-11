package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.command.CommandHandler;
import com.evilmidget38.bukkitguice.config.ConfigTypeListener;
import com.evilmidget38.bukkitguice.listener.ListenerHandler;
import com.evilmidget38.bukkitguice.scanning.JarScanner;
import com.evilmidget38.bukkitguice.services.PluginServiceHandler;
import com.evilmidget38.bukkitguice.services.ServiceManager;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.matcher.Matchers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {
    private final Set<Class<?>> discovered = Sets.newHashSet();
    private final JavaPlugin plugin;
    private final ServiceManager services;

    public PluginModule(JavaPlugin plugin, ServiceManager services) {
        this.plugin = plugin;
        this.services = services;
    }

    public ServiceManager getServices() {
        return services;
    }

    public Set<Class<?>> getDiscovered() {
        return discovered;
    }


    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        // Bind the JavaPlugin instance.
        dynamicBind(plugin.getClass()).toInstance(plugin);

        // Add @Config support
        bindListener(Matchers.any(), new ConfigTypeListener(plugin));
        // Class discovery
        JarScanner scanner = null;
        try {
            scanner = new JarScanner(new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()), plugin.getLogger());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        // Add support for @PluginService, Listeners, and CommandExecutors
        scanner.addHandler(new PluginServiceHandler(this));
        scanner.addHandler(new CommandHandler(this));
        scanner.addHandler(new ListenerHandler(this));
        try {
            scanner.scan();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        services.validateServices(plugin.getLogger());

        for (Map.Entry<Class<?>, Class<?>> entry : services.getBindings().entrySet()) {
            plugin.getLogger().info("Binding " + entry.getKey().getSimpleName() + " to " + entry.getValue().getSimpleName());
            dynamicBind(entry.getKey()).to(entry.getValue());
        }
    }

    // Public and non-generic version of bind.
    public AnnotatedBindingBuilder dynamicBind(Class<?> clazz) {
        return super.bind(clazz);
    }
}
