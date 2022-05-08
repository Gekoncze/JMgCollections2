package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Storage interface ReadableListItem<T> {
    @Mandatory ReadableList<T> getList();
    T get();
    ReadableListItem<T> getPreviousItem();
    ReadableListItem<T> getNextItem();
}
