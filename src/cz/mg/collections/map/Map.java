package cz.mg.collections.map;

import cz.mg.annotations.classes.Group;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.pair.ReadablePair;
import cz.mg.collections.utilities.CompareFunction;
import cz.mg.collections.utilities.CompareFunctions;
import cz.mg.collections.utilities.HashFunction;
import cz.mg.collections.utilities.HashFunctions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Group class Map<K,V> extends Collection<ReadablePair<K,V>> implements ReadableMap<K,V>, WriteableMap<K,V> {
    private final @Mandatory Array<ListItem<MapPair<K,V>>> array;
    private final @Mandatory List<MapPair<K,V>> list;
    private final @Mandatory CompareFunction compareFunction;
    private final @Mandatory HashFunction hashFunction;

    public Map(int cache, @Mandatory CompareFunction compareFunction, @Mandatory HashFunction hashFunction) {
        if (cache < 1) {
            throw new IllegalArgumentException("Cache must be > 0.");
        }

        this.array = new Array<>(cache);
        this.list = new List<>();
        this.compareFunction = compareFunction;
        this.hashFunction = hashFunction;
    }

    public Map(int cache) {
        this(cache, CompareFunctions.EQUALS, HashFunctions.HASH_CODE);
    }

    @SafeVarargs
    public Map(int cache, ReadablePair<K,V>... pairs) {
        this(cache);
        for (ReadablePair<K,V> pair : pairs) {
            set(pair.getKey(), pair.getValue());
        }
    }

    public Map(int cache, @Mandatory Iterable<? extends ReadablePair<K,V>> pairs) {
        this(cache);
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
        ListItem<MapPair<K,V>> item = findItem(key);
        if (item != null) {
            return item.get().getValue();
        } else {
            return null;
        }
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

    private @Optional ListItem<MapPair<K,V>> findItem(K key) {
        ListItem<MapPair<K, V>> startingItem = array.get(getIndex(key));
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
                if (compareFunction.equals(key, pair.getKey())) {
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
        int index = getIndex(key);
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
        int index = getIndex(key);
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

    private int getIndex(K key) {
        return Math.abs(hashFunction.hash(key) % array.count());
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
}
