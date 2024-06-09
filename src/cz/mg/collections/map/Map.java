package cz.mg.collections.map;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.components.*;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.pair.ReadablePair;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Data class Map<K,V> extends Collection<ReadablePair<K,V>> implements ReadableMap<K,V>, WriteableMap<K,V> {
    private final @Mandatory Array<ListItem<MapPair<K,V>>> array;
    private final @Mandatory List<MapPair<K,V>> list;
    private final @Mandatory CompareFunction<K> compareFunction;
    private final @Mandatory HashFunction<K> hashFunction;

    public Map(
        @Mandatory Capacity capacity,
        @Mandatory CompareFunction<K> compareFunction,
        @Mandatory HashFunction<K> hashFunction
    ) {
        this.array = new Array<>(capacity.getValue());
        this.list = new List<>();
        this.compareFunction = compareFunction;
        this.hashFunction = hashFunction;
    }

    public Map(@Mandatory Capacity capacity) {
        this(capacity, CompareFunctions.EQUALS(), HashFunctions.HASH_CODE());
    }

    @SafeVarargs
    public Map(@Mandatory Capacity capacity, ReadablePair<K,V>... pairs) {
        this(capacity);
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public Map(@Mandatory Capacity capacity, @Mandatory Iterable<? extends ReadablePair<K,V>> pairs) {
        this(capacity);
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public int count() {
        return list.count();
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
    public V getOptional(K key) {
        return getOptional(key, null);
    }

    @Override
    public V getOptional(K key, V defaultValue) {
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
    }

    @Override
    public V remove(K key) {
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
                return list.removeItem(targetItem).getValue();
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
        return compareFunction.equalsOptional(a, b);
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

    public interface Factory<V> {
        @Optional V create();
    }
}
