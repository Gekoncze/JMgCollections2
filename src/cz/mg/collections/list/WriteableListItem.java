package cz.mg.collections.list;

import cz.mg.annotations.classes.Group;

public @Group interface WriteableListItem<T> {
    void set(T data);
    T removePrevious();
    T removeNext();
}
