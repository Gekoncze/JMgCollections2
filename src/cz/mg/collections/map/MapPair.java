package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.pair.ReadablePair;

@Storage class MapPair<K,V> implements ReadablePair<K,V> {
    K key;
    V value;
    int index;
    @Optional MapPair<K,V> nextPair;

    MapPair(K key, V value, int index, @Optional MapPair<K,V> nextPair) {
        this.key = key;
        this.value = value;
        this.index = index;
        this.nextPair = nextPair;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }
}
