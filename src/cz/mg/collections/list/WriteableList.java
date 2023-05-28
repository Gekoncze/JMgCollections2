package cz.mg.collections.list;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.WriteableCollection;
import cz.mg.collections.coponents.Predicate;

public @Data interface WriteableList<T> extends WriteableCollection<T> {
    void setFirst(T data);
    void setLast(T data);
    void set(int i, T data);
    void addFirst(T data);
    void addLast(T data);
    void add(int i, T data);
    void addCollectionFirst(@Mandatory ReadableList<? extends T> collection);
    void addCollectionLast(@Mandatory Iterable<? extends T> collection);
    void addNext(@Mandatory ListItem<T> listItem, T data);
    void addPrevious(@Mandatory ListItem<T> listItem, T data);
    T removeFirst();
    T removeLast();
    T remove(int i);
    T removeItem(@Mandatory ListItem<T> listItem);
    void removeIf(@Mandatory Predicate<T> condition);
    void removeItemIf(@Mandatory Predicate<ListItem<T>> condition);
    void clear();
}
