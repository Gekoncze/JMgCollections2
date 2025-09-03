package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.pair.ReadablePair;
import cz.mg.functions.EqualsFunction;
import cz.mg.functions.EqualsFunctions;
import cz.mg.functions.HashFunction;
import cz.mg.functions.HashFunctions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Data class Map<K,V> extends Collection<ReadablePair<K,V>> implements ReadableMap<K,V>, WriteableMap<K,V> {
    private static final int LIMIT = 2;
    public static final int MIN_LOAD = 25;
    public static final int MAX_LOAD = 75;

    private @Mandatory Array<ListItem<MapPair<K,V>>> array;
    private @Mandatory List<MapPair<K,V>> list;
    private final @Mandatory EqualsFunction<K> equalsFunction;
    private final @Mandatory HashFunction<K> hashFunction;

    public Map() {
        this(EqualsFunctions.EQUALS(), HashFunctions.HASH_CODE());
    }

    public Map(
        @Mandatory EqualsFunction<K> equalsFunction,
        @Mandatory HashFunction<K> hashFunction
    ) {
        this.array = new Array<>(LIMIT);
        this.list = new List<>();
        this.equalsFunction = equalsFunction;
        this.hashFunction = hashFunction;
    }

    public Map(@Mandatory Iterable<? extends ReadablePair<K,V>> pairs) {
        this();
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    @SafeVarargs
    public Map(@Mandatory ReadablePair<K,V>... pairs) {
        this();
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public Map(
        @Mandatory Iterable<? extends ReadablePair<K,V>> pairs,
        @Mandatory EqualsFunction<K> equalsFunction,
        @Mandatory HashFunction<K> hashFunction
    ) {
        this(equalsFunction, hashFunction);
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public int count() {
        return list.count();
    }

    @Override
    public int load() {
        return (list.count() * 100) / array.count();
    }

    @Override
    public V get(K key) {
        ListItem<MapPair<K,V>> item = findItem(key);
        if (item != null) {
            return item.get().getValue();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public @Optional V getOptional(K key) {
        return getOrDefault(key, null);
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        ListItem<MapPair<K,V>> item = findItem(key);
        if (item != null) {
            return item.get().getValue();
        } else {
            return defaultValue;
        }
    }

    @Override
    public V getOrCreate(K key, @Mandatory Factory<V> factory) {
        ListItem<MapPair<K,V>> item = findItem(key);
        if (item != null) {
            return item.get().getValue();
        } else {
            V value = factory.create();
            set(key, value);
            return value;
        }
    }

    private @Optional ListItem<MapPair<K,V>> findItem(K key) {
        ListItem<MapPair<K, V>> startingItem = array.get(index(key));
        if (startingItem != null) {
            return findItem(key, startingItem);
        } else {
            return null;
        }
    }

    private @Optional ListItem<MapPair<K,V>> findItem(K key, @Mandatory ListItem<MapPair<K,V>> startingItem) {
        int index = startingItem.get().getIndex();

        for (ListItem<MapPair<K,V>> item = startingItem; item != null; item = item.getNextItem()) {
            MapPair<K,V> pair = item.get();

            if (pair.getIndex() == index) {
                if (equals(key, pair.getKey())) {
                    return item;
                }
            } else {
                break;
            }
        }

        return null;
    }

    @Override
    public void set(K key, V value) {
        int index = index(key);
        ListItem<MapPair<K,V>> startingItem = array.get(index);

        if (startingItem != null) {
            ListItem<MapPair<K,V>> targetItem = findItem(key, startingItem);
            if (targetItem != null) {
                targetItem.get().setValue(value);
            } else {
                list.addNext(startingItem, new MapPair<>(key, value, index));
            }
        } else {
            list.addLast(new MapPair<>(key, value, index));
            array.set(index, list.getLastItem());
        }

        if (load() > MAX_LOAD) {
            expand();
        }
    }

    @Override
    public V unset(K key) {
        int index = index(key);
        ListItem<MapPair<K,V>> startingItem = array.get(index);

        if (startingItem != null) {
            ListItem<MapPair<K,V>> targetItem = findItem(key, startingItem);
            if (targetItem != null) {
                if (targetItem == startingItem) {
                    ListItem<MapPair<K, V>> nextItem = targetItem.getNextItem();
                    if (nextItem != null) {
                        if (nextItem.get().getIndex() == index) {
                            array.set(index, nextItem);
                        } else {
                            array.set(index, null);
                        }
                    } else {
                        array.set(index, null);
                    }
                }
                V value = list.removeItem(targetItem).getValue();
                if (load() < MIN_LOAD) {
                    shrink();
                }
                return value;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    private int index(K key) {
        return Math.abs(hashFunction.hashOptional(key) % array.count());
    }

    private boolean equals(K a, K b) {
        return equalsFunction.equalsOptional(a, b);
    }

    @Override
    public void clear() {
        array.wipe();
        list.clear();
    }

    @Override
    public @Mandatory Iterator<ReadablePair<K,V>> iterator() {
        return new Iterator<>() {
            private final Iterator<MapPair<K,V>> iterator = list.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public ReadablePair<K, V> next() {
                return iterator.next();
            }
        };
    }

    private void expand() {
        List<MapPair<K,V>> pairs = list;
        array = new Array<>(array.count() * 2);
        list = new List<>();
        for (MapPair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    private void shrink() {
        if (array.count() <= LIMIT) return;
        List<MapPair<K,V>> pairs = list;
        array = new Array<>(array.count() / 2);
        list = new List<>();
        for (MapPair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public interface Factory<V> {
        @Optional V create();
    }
}
