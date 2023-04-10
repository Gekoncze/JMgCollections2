package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.collections.pair.ReadablePair;

@Data class MapPair<K,V> implements ReadablePair<K,V> {
    private K key;
    private V value;
    private int index;

    MapPair(K key, V value, int index) {
        this.key = key;
        this.value = value;
        this.index = index;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
