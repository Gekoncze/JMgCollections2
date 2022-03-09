package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;

public @Storage interface ReadableMap<K,V> {
    V get(K key);
    V getOrDefault(K key, V defaultValue);
}
