package com.evilmidget38.bukkitguice.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface PluginVersion {
    public String plugin();
    public String version();
}
