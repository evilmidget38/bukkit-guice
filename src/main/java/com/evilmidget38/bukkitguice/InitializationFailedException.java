package com.evilmidget38.bukkitguice;

public class InitializationFailedException extends Exception {
    public InitializationFailedException(String cause) {
        super(cause);
    }

    public InitializationFailedException(Throwable cause) {
        super(cause);
    }
}
