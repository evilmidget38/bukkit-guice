package com.evilmidget38.bukkitguice.services;

import java.lang.annotation.Annotation;

public interface Constraint<T extends Annotation> {
    public boolean apply(T t);
}
