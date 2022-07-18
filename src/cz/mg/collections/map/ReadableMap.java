package cz.mg.collections.map;

import cz.mg.annotations.classes.Group;
import cz.mg.collections.pair.ReadablePair;

public @Group interface ReadableMap<K,V> extends Iterable<ReadablePair<K,V>> {
    V get(K key);
    V getOptional(K key);
    V getOrDefault(K key, V defaultValue);
}
