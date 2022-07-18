package cz.mg.collections.array;

import cz.mg.annotations.classes.Group;
import cz.mg.collections.WriteableCollection;

public @Group interface WriteableArray<T> extends WriteableCollection<T> {
    void set(int i, T item);
}
