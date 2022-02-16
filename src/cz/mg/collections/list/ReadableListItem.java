package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage interface ReadableListItem<T> {
    T get();
    @Optional ReadableListItem<T> getPreviousItem();
    @Optional ReadableListItem<T> getNextItem();
}
