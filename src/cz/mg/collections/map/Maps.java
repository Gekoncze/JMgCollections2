package cz.mg.collections.map;

import cz.mg.annotations.classes.Static;
import cz.mg.collections.pair.ReadablePair;

public @Static class Maps {
    @SafeVarargs
    public static <K,V> Map<K,V> create(ReadablePair<K,V>... pairs) {
        Map<K,V> map = new Map<>();
        for (ReadablePair<K,V> pair : pairs) {
            map.set(pair.getKey(), pair.getValue());
        }
        return map;
    }
}
