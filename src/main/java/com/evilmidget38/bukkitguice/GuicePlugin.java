package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.command.CommandInitializer;
import com.google.common.collect.ImmutableList;
import com.google.inject.Module;

import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A utility class for easily adding BukkitGuice support.
 */
public class GuicePlugin extends JavaPlugin {
    @Override
    public final void onEnable() {
        BukkitGuice guice = new BukkitGuice(this);
        configure(guice);
        guice.start();
        start();
    }

    public void configure(BukkitGuice guice) {}

    public void start() {}
}
