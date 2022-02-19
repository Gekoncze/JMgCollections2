package cz.mg.collections.map;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.pair.Pair;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public @Storage class Map<K,V> extends Collection<MapPair<K,V>> implements ReadableMap<K,V>, WriteableMap<K,V> {
    private final @Mandatory MapPair<K,V>[] array;
    private @Optional MapPair<K,V> first;
    private int count;

    public Map(int size) {
        if(size < 1) {
            throw new IllegalArgumentException("Map size must be > 0.");
        }

        //noinspection unchecked
        array = new MapPair[size];
    }

    @SafeVarargs
    public Map(int size, Pair<K,V>... pairs) {
        this(size);
        for(Pair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public Map(int size, @Mandatory Iterable<Pair<K,V>> pairs) {
        this(size);
        for(Pair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public Map(int size, @Mandatory Map<K,V> map) {
        this(size);
        for(MapPair<K,V> pair : map) {
            set(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        MapPair<K,V> pair = array[index];

        while(pair != null && pair.index == index) {
            if(Objects.equals(key, pair.key)) {
                return pair.value;
            } else {
                pair = pair.nextPair;
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    public void set(K key, V value) {
        int index = getIndex(key);
        MapPair<K,V> pair = array[index];
        MapPair<K,V> last = null;

        while(pair != null && pair.index == index) {
            last = pair;

            if(Objects.equals(key, pair.key)) {
                pair.value = value;
                return;
            } else {
                pair = pair.nextPair;
            }
        }

        if(last != null) {
            last.nextPair = new MapPair<>(key, value, index, last.nextPair);
        } else {
            first = new MapPair<>(key, value, index, first);
            array[index] = first;
        }

        count++;
    }

    private int getIndex(K key) {
        return Math.abs(Objects.hashCode(key) % array.length);
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        first = null;
        count = 0;
    }

    @Override
    public Iterator<MapPair<K, V>> iterator() {
        return new Iterator<>() {
            private @Optional MapPair<K,V> pair = first;

            @Override
            public boolean hasNext() {
                return pair != null;
            }

            @Override
            public MapPair<K, V> next() {
                if(hasNext()) {
                    MapPair<K,V> result = pair;
                    pair = pair.nextPair;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
