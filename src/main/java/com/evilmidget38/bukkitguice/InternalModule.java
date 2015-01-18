package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.command.CommandHandler;
import com.evilmidget38.bukkitguice.command.CommandInitializer;
import com.evilmidget38.bukkitguice.config.ConfigProcessor;
import com.evilmidget38.bukkitguice.injectors.TypeListenerBinding;
import com.evilmidget38.bukkitguice.listener.ListenerHandler;
import com.evilmidget38.bukkitguice.listener.ListenerInitializer;
import com.evilmidget38.bukkitguice.plugin.PluginProcessor;
import com.evilmidget38.bukkitguice.scanning.ClassHandler;
import com.evilmidget38.bukkitguice.services.DefaultServiceManager;
import com.evilmidget38.bukkitguice.services.PluginServiceHandler;
import com.evilmidget38.bukkitguice.services.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import java.util.HashSet;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class InternalModule extends AbstractModule {
    private final JavaPlugin plugin;

    public InternalModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(TypeLiterals.SET_CLASS).annotatedWith(Names.named("discovered")).toInstance(new HashSet<Class<?>>());
        bind(Plugin.class).toInstance(plugin);
        bind(ServiceManager.class).to(DefaultServiceManager.class);
        Multibinder<Module> modules = Multibinder.newSetBinder(binder(), Module.class);
        modules.addBinding().to(PluginModule.class);
        Multibinder<TypeListenerBinding> typeListeners = Multibinder.newSetBinder(binder(), TypeListenerBinding.class);
        typeListeners.addBinding().to(ConfigProcessor.class);
        typeListeners.addBinding().to(PluginProcessor.class);
        Multibinder<ClassHandler> classHandlers = Multibinder.newSetBinder(binder(), ClassHandler.class);
        classHandlers.addBinding().to(ListenerHandler.class);
        classHandlers.addBinding().to(CommandHandler.class);
        classHandlers.addBinding().to(PluginServiceHandler.class);
        Multibinder<ObjectInitializer> initializers = Multibinder.newSetBinder(binder(), ObjectInitializer.class);
        initializers.addBinding().to(CommandInitializer.class);
        initializers.addBinding().to(ListenerInitializer.class);
    }
}
