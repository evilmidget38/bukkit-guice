package com.evilmidget38.bukkitguice.injectors;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AnnotationProcessor<ANNOTATION extends Annotation> implements TypeListener {
    private final Class<ANNOTATION> annotation;

    protected AnnotationProcessor(Class<ANNOTATION> annotation) {
        this.annotation = annotation;
    }

    @Override
    public <I> void hear(TypeLiteral<I> literal, TypeEncounter<I> encounter) {
        Class<?> type = literal.getRawType();
        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() == FileConfiguration.class) {
                    ANNOTATION annotation = field.getAnnotation(this.annotation);
                    if (annotation != null) {
                        encounter.register(new FieldInjector<I>(field, get(annotation)));
                    }
                }
            }
            type = type.getSuperclass();
        }
    }

    public abstract Object get(ANNOTATION annotation);
}
