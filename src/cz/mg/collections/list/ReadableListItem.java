package cz.mg.collections.list;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;

public @Data interface ReadableListItem<T> {
    @Mandatory ReadableList<T> getList();
    T get();
    ReadableListItem<T> getPreviousItem();
    ReadableListItem<T> getNextItem();
}
