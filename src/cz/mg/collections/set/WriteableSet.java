package cz.mg.collections.set;

import cz.mg.annotations.classes.Group;
import cz.mg.collections.WriteableCollection;

public @Group interface WriteableSet<T> extends WriteableCollection<T> {
    void set(T data);
    void remove(T data);
    void clear();
}
