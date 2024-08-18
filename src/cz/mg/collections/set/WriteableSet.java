package cz.mg.collections.set;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.WriteableCollection;

public @Data interface WriteableSet<T> extends WriteableCollection<T> {
    void set(T data);
    void unset(T data);
    void setCollection(ReadableCollection<T> collection);
    void unsetCollection(ReadableCollection<T> collection);
    void clear();
}
