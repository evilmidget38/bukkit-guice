package com.evilmidget38.bukkitguice.scanning;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JarScanner {
    private final List<ClassHandler> handlers = new ArrayList<>();
    private final File jarFile;
    private final Logger logger;

    public JarScanner(File jarFile, Logger logger) {
        this.jarFile = jarFile;
        this.logger = logger;
    }

    public void addHandler(ClassHandler handler) {
        handlers.add(handler);
    }

    public void scan() throws IOException {
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entryEnumeration = jar.entries();

        for (ClassHandler handler : handlers) {
            handler.start();
        }

        while (entryEnumeration.hasMoreElements()) {
            JarEntry entry = entryEnumeration.nextElement();
            // Check for class files, and ignore  anything not in the root of the compilation unit.
            // Nested classes and anonymous classes are packaged in the format "{package}/{containing type}${type}.class"
            if (entry.getName().endsWith(".class") && !entry.getName().contains("$")) {
                String entryName = entry.getName();
                entryName = entryName.replace("/", ".").replace("\\\\", ".").replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(entryName);

                    for (ClassHandler handler : handlers) {
                        handler.handle(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Failed to find class located in plugin jar file.", e);
                }
            }
        }
        for (ClassHandler handler : handlers) {
            handler.end();
        }
    }
}
