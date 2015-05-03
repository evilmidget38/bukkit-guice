# bukkit-guice
Bukkit Guice is a project I've played around with in my spare time.  I wrote the core of it in a weekend, and haven't really worked a whole lot on it since.  If you want to use it, feel free.  Just don't look at the code.  It's... scary.

## Usage
There are two main methods for creating a plugin that utilizes Bukkit-Guice.  Both should provide equivalent results, however the first option is significantly easier.
### Extending GuicePlugin
Extending GuicePlugin results in all classpath scanning occurring automatically.  Customization can be performed by overriding GuicePlugin#configure(BukkitGuice).
Initialization can be performed by overriding GuicePlugin#start().  Fields will be injected into the class, and any @Provides annotations will be read, allowing for easy configuration without having to override GuicePlugin#configure(BukkitGuice).
JavaPlugin#onEnable() is overriden by GuicePlugin and marked as final, to ensure that no initialization occurs before injection.  If an error occurs during initialization, a stacktrace will be printed using the plugin's logger, and the plugin will disable itself.

# Still working on the rest
