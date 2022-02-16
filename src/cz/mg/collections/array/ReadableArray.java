package cz.mg.collections.array;

import cz.mg.annotations.classes.Storage;
import cz.mg.collections.ReadableCollection;

public @Storage interface ReadableArray<T> extends ReadableCollection<T> {
    T get(int i);
}
