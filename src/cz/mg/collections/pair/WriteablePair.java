package cz.mg.collections.pair;

import cz.mg.annotations.classes.Storage;

public @Storage interface WriteablePair<K,V> {
    void setKey(K key);
    void setValue(V value);
}
