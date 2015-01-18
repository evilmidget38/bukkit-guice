package com.evilmidget38.bukkitguice.injectors;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.TypeListener;

public interface TypeListenerBinding extends TypeListener {
    public Matcher<? super TypeLiteral<?>> getMatcher();

}
