package cz.mg.collections.array;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.ReadableCollection;

public @Data interface ReadableArray<T> extends ReadableCollection<T> {
    T get(int i);
}
