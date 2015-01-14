package com.evilmidget38.bukkitguice;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A utility class for easily adding BukkitGuice support.
 */
public class GuicePlugin extends JavaPlugin {
    @Override
    public final void onEnable() {
        BukkitGuice guice = new BukkitGuice(this);
        configure(guice);
        try {
            guice.start();
        } catch (InitializationFailedException e) {
            getLogger().log(Level.SEVERE, "Failed to start " + getDescription().getName(), e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        start();
    }

    public void configure(BukkitGuice guice) {}

    public void start() {}
}
