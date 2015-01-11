package com.evilmidget38.bukkitguice;

import com.evilmidget38.bukkitguice.services.Constraint;
import org.bukkit.Bukkit;

public class McVersionConstraint implements Constraint<McVersion> {
    @Override
    public boolean apply(McVersion mcVersion) {
        return mcVersion.value().equalsIgnoreCase(getMinecraftVersion());
    }

    public static String getMinecraftVersion() {
        String versionedPackage = Bukkit.getServer().getClass().getPackage().getName();

        // Add 2 to exclude ".v"
        return versionedPackage.substring(versionedPackage.lastIndexOf('.') + 2);
    }
}
