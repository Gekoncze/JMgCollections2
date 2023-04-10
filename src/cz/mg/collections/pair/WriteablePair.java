package cz.mg.collections.pair;

import cz.mg.annotations.classes.Data;

public @Data interface WriteablePair<K,V> {
    void setKey(K key);
    void setValue(V value);
}
