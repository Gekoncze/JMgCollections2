package cz.mg.collections.set;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.ReadableCollection;

public @Data interface ReadableSet<T> extends ReadableCollection<T> {
    int load();
}
