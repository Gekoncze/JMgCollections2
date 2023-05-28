package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;

public @Component interface Predicate<T> {
    boolean match(T object);
}
