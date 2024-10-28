package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.pair.ReadablePair;

public @Data interface ReadableMap<K,V> extends Iterable<ReadablePair<K,V>> {
    int load();
    V get(K key);
    @Optional V getOptional(K key);
    V getOrDefault(K key, V defaultValue);
}
