package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;

public @Data interface WriteableMap<K,V> {
    void set(K key, V value);
    V remove(K key);
    void clear();
}
