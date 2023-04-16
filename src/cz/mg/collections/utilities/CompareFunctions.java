package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Static;

import java.util.Objects;

public @Static class CompareFunctions {
    public static <T> CompareFunction<T> EQUALS() {
        return Objects::equals;
    }

    public static <T> CompareFunction<T> REFERENCE() {
        return (a, b) -> a == b;
    }
}
