package cz.mg.collections.map;

import cz.mg.annotations.classes.Group;

public @Group interface WriteableMap<K,V> {
    void set(K key, V value);
    V remove(K key);
    void clear();
}
