package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;

public @Data interface WriteableMap<K,V> {
    V getOrCreate(K key, @Mandatory Map.Factory<V> factory);
    void set(K key, V value);
    V remove(K key);
    void clear();
}
