package com.evilmidget38.bukkitguice.services;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.logging.Logger;

public interface ServiceManager {
    public void addConstraint(Class<? extends Annotation> annotation, Constraint<?> constraint);
    public void registerClass(Class<?> service, Class<?> clazz);
    public void validateServices(Logger logger);
    public Map<Class<?>, Class<?>> getBindings();
}
