package com.evilmidget38.bukkitguice.scanning;

public interface ClassHandler {
    public void start();
    public void handle(Class<?> clazz);
    public void end();
}
