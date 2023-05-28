package cz.mg.collections.components;

import cz.mg.annotations.classes.Static;

import java.util.Objects;

public @Static class HashFunctions<T> {
    public static <T> HashFunction<T> HASH_CODE() {
        return Objects::hashCode;
    }
}
