package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;

public @Storage interface WriteableListItem<T> {
    void set(T data);
}
