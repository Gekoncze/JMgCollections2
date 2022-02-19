package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage interface WriteableMapPair<K,V> {
    void setValue(@Optional V value);
}
