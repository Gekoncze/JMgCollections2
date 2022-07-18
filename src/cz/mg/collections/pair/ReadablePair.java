package cz.mg.collections.pair;

import cz.mg.annotations.classes.Group;

public @Group interface ReadablePair<K,V> {
    K getKey();
    V getValue();
}
