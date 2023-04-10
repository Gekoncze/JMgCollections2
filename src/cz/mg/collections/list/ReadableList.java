package cz.mg.collections.list;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.ReadableCollection;

public @Data interface ReadableList<T> extends ReadableCollection<T> {
    T getFirst();
    T getLast();
    T get(int i);
    ReadableListItem<T> getItem(int i);
    ReadableListItem<T> getFirstItem();
    ReadableListItem<T> getLastItem();
}
