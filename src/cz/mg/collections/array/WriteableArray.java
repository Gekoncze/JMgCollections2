package cz.mg.collections.array;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.WriteableCollection;

public @Data interface WriteableArray<T> extends WriteableCollection<T> {
    void set(int i, T item);
}
