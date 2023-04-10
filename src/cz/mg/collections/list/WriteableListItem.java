package cz.mg.collections.list;

import cz.mg.annotations.classes.Data;

public @Data interface WriteableListItem<T> {
    void set(T data);
    T removePrevious();
    T removeNext();
}
