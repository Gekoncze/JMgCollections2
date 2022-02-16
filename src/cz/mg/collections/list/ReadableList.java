package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.ReadableCollection;

public @Storage interface ReadableList<T> extends ReadableCollection<T> {
    T getFirst();
    T getLast();
    T get(int i);
    @Optional ReadableListItem<T> getFirstItem();
    @Optional ReadableListItem<T> getLastItem();
}
