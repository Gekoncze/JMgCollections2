package cz.mg.collections;

import cz.mg.annotations.classes.Storage;

public @Storage abstract class Collection<T> implements ReadableCollection<T>, WriteableCollection<T> {
}
