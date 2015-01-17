package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.command.CommandInitializer;
import com.evilmidget38.bukkitguice.listener.ListenerInitializer;
import com.evilmidget38.bukkitguice.minecraft.McVersion;
import com.evilmidget38.bukkitguice.minecraft.McVersionConstraint;
import com.evilmidget38.bukkitguice.plugin.PluginVersion;
import com.evilmidget38.bukkitguice.plugin.PluginVersionConstraint;
import com.evilmidget38.bukkitguice.services.Constraint;
import com.evilmidget38.bukkitguice.services.ServiceManager;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.lang.annotation.Annotation;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitGuice {
    private final JavaPlugin plugin;
    private final List<ObjectInitializer> initializers;
    private final ServiceManager serviceManager = new ServiceManager();


    public BukkitGuice(JavaPlugin plugin) {
        this.plugin = plugin;
        this.initializers = Lists.<ObjectInitializer>newArrayList(new CommandInitializer(), new ListenerInitializer());
        addConstraint(McVersion.class, new McVersionConstraint());
        addConstraint(PluginVersion.class, new PluginVersionConstraint());
    }

    public <T extends Annotation> void addConstraint(Class<T> clazz, Constraint<T> constraint) {
        serviceManager.addConstraint(clazz, constraint);
    }

    public void addObjectInitializer(ObjectInitializer<?> initializer) {
        initializers.add(initializer);
    }

    @SuppressWarnings("unchecked")
    public void start(Module...modules) throws InitializationFailedException {
        PluginModule pluginModule = new PluginModule(plugin, serviceManager);
        Module[] completeModules = new Module[modules.length+1];
        System.arraycopy(modules, 0, completeModules, 0, modules.length);
        completeModules[modules.length] = pluginModule;

        try {
            Injector injector = Guice.createInjector(completeModules);
            serviceManager.validateServices(plugin.getLogger());
            for (Class<?> clazz : pluginModule.getDiscovered()) {
                Object object = injector.getInstance(clazz);
                for (ObjectInitializer initializer : initializers) {
                    if (initializer.getType().isAssignableFrom(clazz)) {
                        initializer.initialize(plugin, object);
                    }
                }
            }
        } catch (Exception e) {
            throw new InitializationFailedException(e);
        }
    }

}
