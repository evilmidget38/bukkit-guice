package com.evilmidget38.bukkitguice.plugin;

import com.evilmidget38.bukkitguice.services.Constraint;
import org.bukkit.Bukkit;

public class PluginVersionConstraint implements Constraint<PluginVersion> {
    @Override
    public boolean apply(PluginVersion pluginVersion) {
        org.bukkit.plugin.Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginVersion.plugin());
        return plugin != null && plugin.getDescription().getVersion().equalsIgnoreCase(pluginVersion.version());
    }
}
