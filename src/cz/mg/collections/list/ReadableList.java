package cz.mg.collections.list;

import cz.mg.annotations.classes.Group;
import cz.mg.collections.ReadableCollection;

public @Group interface ReadableList<T> extends ReadableCollection<T> {
    T getFirst();
    T getLast();
    T get(int i);
    ReadableListItem<T> getFirstItem();
    ReadableListItem<T> getLastItem();
}
