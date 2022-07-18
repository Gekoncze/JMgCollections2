package cz.mg.collections.array;

import cz.mg.annotations.classes.Group;
import cz.mg.collections.ReadableCollection;

public @Group interface ReadableArray<T> extends ReadableCollection<T> {
    T get(int i);
}
