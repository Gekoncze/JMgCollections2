package cz.mg.collections.pair;

import cz.mg.annotations.classes.Group;

public @Group interface WriteablePair<K,V> {
    void setKey(K key);
    void setValue(V value);
}
