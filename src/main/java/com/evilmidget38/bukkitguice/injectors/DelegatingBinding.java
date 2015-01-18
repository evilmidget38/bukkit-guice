package com.evilmidget38.bukkitguice.injectors;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class DelegatingBinding implements TypeListenerBinding {
    private final Matcher<?> matcher;
    private final TypeListener listener;

    public DelegatingBinding(Matcher<?> matcher, TypeListener listener) {
        this.matcher = matcher;
        this.listener = listener;
    }

    public <I> void hear(TypeLiteral<I> literal, TypeEncounter<I> encounter) {
        listener.hear(literal, encounter);
    }

    @Override
    public Matcher<?> getMatcher() {
        return matcher;
    }

    public static TypeListenerBinding of(final Matcher<?> matcher, final TypeListener listener) {
        return new DelegatingBinding(matcher, listener);
    }
}
