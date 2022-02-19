package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage class MapPair<K,V> implements ReadableMapPair<K,V>, WriteableMapPair<K,V> {
    K key;
    V value;
    int index;
    @Optional MapPair<K,V> nextPair;

    protected MapPair(K key, V value, int index, @Optional MapPair<K,V> nextPair) {
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

    @Override
    public void setValue(@Optional V value) {
        this.value = value;
    }
}
