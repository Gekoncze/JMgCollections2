package cz.mg.collections.set;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.array.Array;
import cz.mg.collections.components.*;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Data class Set<T> extends Collection<T> implements ReadableSet<T>, WriteableSet<T> {
    private static final int LIMIT = 2;
    public static final int MIN_LOAD = 25;
    public static final int MAX_LOAD = 75;

    private @Mandatory Array<ListItem<SetItem<T>>> array;
    private @Mandatory List<SetItem<T>> list;
    private final @Mandatory CompareFunction<T> compareFunction;
    private final @Mandatory HashFunction<T> hashFunction;

    public Set() {
        this(CompareFunctions.EQUALS(), HashFunctions.HASH_CODE());
    }

    public Set(
        @Mandatory CompareFunction<T> compareFunction,
        @Mandatory HashFunction<T> hashFunction
    ) {
        this.array = new Array<>(LIMIT);
        this.list = new List<>();
        this.compareFunction = compareFunction;
        this.hashFunction = hashFunction;
    }

    public Set(@Mandatory Iterable<? extends T> values) {
        this();
        for (T value : values) {
            set(value);
        }
    }

    public Set(
        @Mandatory Iterable<? extends T> values,
        @Mandatory CompareFunction<T> compareFunction,
        @Mandatory HashFunction<T> hashFunction
    ) {
        this(compareFunction, hashFunction);
        for (T value : values) {
            set(value);
        }
    }

    @SafeVarargs
    public Set(@Mandatory T... values) {
        this();
        for (T value : values) {
            set(value);
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

        if (load() > MAX_LOAD) {
            expand();
        }
    }

    @Override
    public void unset(T value) {
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

        if (load() < MIN_LOAD) {
            shrink();
        }
    }

    @Override
    public void setCollection(ReadableCollection<T> collection) {
        for (T value : collection) {
            set(value);
        }
    }

    @Override
    public void unsetCollection(ReadableCollection<T> collection) {
        for (T value : collection) {
            unset(value);
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

    private void expand() {
        List<SetItem<T>> items = list;
        array = new Array<>(array.count() * 2);
        list = new List<>();
        for (SetItem<T> item : items) {
            set(item.get());
        }
    }

    private void shrink() {
        if (array.count() <= LIMIT) return;
        List<SetItem<T>> items = list;
        array = new Array<>(array.count() / 2);
        list = new List<>();
        for (SetItem<T> item : items) {
            set(item.get());
        }
    }
}
