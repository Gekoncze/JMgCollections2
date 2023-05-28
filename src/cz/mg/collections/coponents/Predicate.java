package cz.mg.collections.coponents;

import cz.mg.annotations.classes.Component;

public @Component interface Predicate<T> {
    boolean match(T object);
}
