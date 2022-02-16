package cz.mg.collections.array;

import cz.mg.annotations.classes.Storage;
import cz.mg.collections.WriteableCollection;

public @Storage interface WriteableArray<T> extends WriteableCollection<T> {
    void set(T item, int i);
}
