package com.evilmidget38.bukkitguice;


import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import java.util.Set;

/**
 * A class containing static instances of {@link com.google.inject.Key}
 * objects used with Guice.  This is done so that another anonymous class
 * isn't added each time a lookup is performed against one of these keys.
 */
public final class TypeLiterals {
    public static final TypeLiteral<Set<Class<?>>> SET_CLASS = new TypeLiteral<Set<Class<?>>>() {};
    public static final TypeLiteral<Set<Module>> SET_MODULE = new TypeLiteral<Set<Module>>() {};
    public static final TypeLiteral<Set<ObjectInitializer<?>>> SET_OBJECT_INITIALIZER = new TypeLiteral<Set<ObjectInitializer<?>>>() {};
}
