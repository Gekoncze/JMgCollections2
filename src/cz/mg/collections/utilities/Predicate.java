package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;

public @Utility interface Predicate<T> {
    boolean match(T object);
}
