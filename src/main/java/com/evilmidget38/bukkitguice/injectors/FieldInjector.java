package com.evilmidget38.bukkitguice.injectors;

import com.google.inject.MembersInjector;
import java.lang.reflect.Field;

public class FieldInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final Object toInject;

    public FieldInjector(Field field, Object toInject) {
        this.field = field;
        this.toInject = toInject;
    }

    @Override
    public void injectMembers(T t) {
        try {
            field.setAccessible(true);
            field.set(t, toInject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
