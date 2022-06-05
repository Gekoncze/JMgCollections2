package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;

public @Storage interface WriteableMap<K,V> {
    void set(K key, V value);
    V remove(K key);
    void clear();
}
