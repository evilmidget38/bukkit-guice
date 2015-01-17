package com.evilmidget38.bukkitguice.plugin;


import com.evilmidget38.bukkitguice.injectors.AnnotationProcessor;
import org.bukkit.Bukkit;

public class PluginProcessor extends AnnotationProcessor<Plugin> {

    public PluginProcessor() {
        super(Plugin.class);
    }

    @Override
    public Object get(Plugin annotation) {
        return Bukkit.getPluginManager().getPlugin(annotation.value());
    }
}
