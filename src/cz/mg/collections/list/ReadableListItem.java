package cz.mg.collections.list;

import cz.mg.annotations.classes.Group;
import cz.mg.annotations.requirement.Mandatory;

public @Group interface ReadableListItem<T> {
    @Mandatory ReadableList<T> getList();
    T get();
    ReadableListItem<T> getPreviousItem();
    ReadableListItem<T> getNextItem();
}
