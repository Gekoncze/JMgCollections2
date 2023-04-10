package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.pair.ReadablePair;

public @Data interface ReadableMap<K,V> extends Iterable<ReadablePair<K,V>> {
    V get(K key);
    V getOptional(K key);
    V getOrDefault(K key, V defaultValue);
}
