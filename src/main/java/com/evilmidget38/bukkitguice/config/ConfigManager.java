package com.evilmidget38.bukkitguice.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final Map<String, FileConfiguration> cache = new HashMap<>();
    private final File dataFolder;
    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public FileConfiguration get(String name, boolean copyNew) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        File file = new File(dataFolder, name);
        FileConfiguration configuration = null;
        if (!file.exists()) {
            try (InputStream resource = plugin.getResource(name)) {
                if ( resource != null) {
                    Files.copy(resource, file.toPath());
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to read '" + name +"' from jar.", e);
            }
        } else if (copyNew) {
            try (BufferedReader resource = new BufferedReader(new InputStreamReader(plugin.getResource(name)))) {
                FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(resource);
                configuration = YamlConfiguration.loadConfiguration(file);
                for (String key : defaultConfig.getKeys(true)) {
                    if (!configuration.isSet(key)) {
                        configuration.set(key, defaultConfig.get(key));
                    }
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to read '" + name + "' from jar.", e);
            }
        }
        configuration = configuration == null ? YamlConfiguration.loadConfiguration(file) : configuration;
        cache.put(name, configuration);
        return configuration;
    }
}
