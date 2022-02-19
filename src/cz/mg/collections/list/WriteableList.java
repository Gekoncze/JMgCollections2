package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.collections.WriteableCollection;

public @Storage interface WriteableList<T> extends WriteableCollection<T> {
    void setFirst(T data);
    void setLast(T data);
    void set(int i, T data);
    void addFirst(T data);
    void addLast(T data);
    void add(int i, T data);
    T removeFirst();
    T removeLast();
    T remove(int i);
    void clear();
}
