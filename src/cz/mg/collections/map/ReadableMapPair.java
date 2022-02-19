package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage interface ReadableMapPair<K,V> {
    @Optional K getKey();
    @Optional V getValue();
}
