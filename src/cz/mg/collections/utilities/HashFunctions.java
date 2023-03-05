package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;

import java.util.Objects;

public @Utility class HashFunctions<T> {
    public static <T> HashFunction<T> HASH_CODE() {
        return Objects::hashCode;
    }
}
