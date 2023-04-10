package cz.mg.collections.pair;

import cz.mg.annotations.classes.Data;

public @Data interface ReadablePair<K,V> {
    K getKey();
    V getValue();
}
