package cz.mg.collections.set;

import cz.mg.annotations.classes.Static;

public @Static class Sets {
    @SafeVarargs
    public static <T> Set<T> create(T... values) {
        Set<T> set = new Set<>();
        for (T value : values) {
            set.set(value);
        }
        return set;
    }

    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> set = new Set<>();
        for (T value : a) {
            set.set(value);
        }
        for (T value : b) {
            set.set(value);
        }
        return set;
    }
}
