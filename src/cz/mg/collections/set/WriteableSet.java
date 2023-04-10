package cz.mg.collections.set;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.WriteableCollection;

public @Data interface WriteableSet<T> extends WriteableCollection<T> {
    void set(T data);
    void remove(T data);
    void clear();
}
