package cz.mg.collections.pair;

import cz.mg.annotations.classes.Storage;

public @Storage interface ReadablePair<K,V> {
    K getKey();
    V getValue();
}
