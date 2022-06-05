package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.collections.ReadableCollection;

public @Storage interface ReadableList<T> extends ReadableCollection<T> {
    T getFirst();
    T getLast();
    T get(int i);
    ReadableListItem<T> getFirstItem();
    ReadableListItem<T> getLastItem();
}
