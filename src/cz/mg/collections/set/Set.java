package cz.mg.collections.set;

import cz.mg.annotations.classes.Group;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.utilities.CompareFunction;
import cz.mg.collections.utilities.CompareFunctions;
import cz.mg.collections.utilities.HashFunction;
import cz.mg.collections.utilities.HashFunctions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Group class Set<T> extends Collection<T> implements ReadableSet<T>, WriteableSet<T> {
    private final @Mandatory Array<ListItem<SetItem<T>>> array;
    private final @Mandatory List<SetItem<T>> list;
    private final @Mandatory CompareFunction<T> compareFunction;
    private final @Mandatory HashFunction<T> hashFunction;

    public Set(int cache, @Mandatory CompareFunction<T> compareFunction, @Mandatory HashFunction<T> hashFunction) {
        if (cache < 1) {
            throw new IllegalArgumentException("Cache must be > 0.");
        }

        this.array = new Array<>(cache);
        this.list = new List<>();
        this.compareFunction = compareFunction;
        this.hashFunction = hashFunction;
    }

    public Set(int cache) {
        this(cache, CompareFunctions.EQUALS(), HashFunctions.HASH_CODE());
    }

    @SafeVarargs
    public Set(int cache, T... values) {
        this(cache);
        for (T value : values) {
            set(value);
        }
    }

    public Set(int cache, @Mandatory Iterable<? extends T> values) {
        this(cache);
        for (T value : values) {
            set(value);
        }
    }

    @Override
    public int count() {
        return list.count();
    }

    @Override
    public boolean contains(@Optional T value) {
        return findItem(value) != null;
    }

    private @Optional ListItem<SetItem<T>> findItem(T value) {
        ListItem<SetItem<T>> startingItem = array.get(index(value));
        if (startingItem != null) {
            return findItem(value, startingItem);
        } else {
            return null;
        }
    }

    private @Optional ListItem<SetItem<T>> findItem(T value, @Mandatory ListItem<SetItem<T>> startingItem) {
        int index = startingItem.get().getIndex();

        for (ListItem<SetItem<T>> listItem = startingItem; listItem != null; listItem = listItem.getNextItem()) {
            SetItem<T> setItem = listItem.get();

            if (setItem.getIndex() == index) {
                if (equals(value, setItem.get())) {
                    return listItem;
                }
            } else {
                break;
            }
        }

        return null;
    }

    @Override
    public void set(T value) {
        int index = index(value);
        ListItem<SetItem<T>> startingItem = array.get(index);

        if (startingItem != null) {
            ListItem<SetItem<T>> targetItem = findItem(value, startingItem);
            if (targetItem == null) {
                list.addNext(startingItem, new SetItem<>(value, index));
            }
        } else {
            list.addLast(new SetItem<>(value, index));
            array.set(index, list.getLastItem());
        }
    }

    @Override
    public void remove(T value) {
        int index = index(value);
        ListItem<SetItem<T>> startingItem = array.get(index);

        if (startingItem != null) {
            ListItem<SetItem<T>> targetItem = findItem(value, startingItem);
            if (targetItem != null) {
                if (targetItem == startingItem) {
                    ListItem<SetItem<T>> nextItem = targetItem.getNextItem();
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
                list.removeItem(targetItem).get();
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    private int index(T value) {
        return Math.abs(hashFunction.hashOptional(value) % array.count());
    }

    private boolean equals(T a, T b) {
        return compareFunction.equalsOptional(a, b);
    }

    @Override
    public void clear() {
        array.wipe();
        list.clear();
    }

    @Override
    public @Mandatory Iterator<T> iterator() {
        return new Iterator<>() {
            private final Iterator<SetItem<T>> iterator = list.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next().get();
            }
        };
    }
}