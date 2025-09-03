package cz.mg.collections.components;

import cz.mg.annotations.classes.Static;

import java.util.Objects;

public @Static class EqualsFunctions {
    public static <T> EqualsFunction<T> EQUALS() {
        return Objects::equals;
    }

    public static <T> EqualsFunction<T> REFERENCE() {
        return (a, b) -> a == b;
    }
}
