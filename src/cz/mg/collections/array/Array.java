package cz.mg.collections.array;

import cz.mg.annotations.classes.Group;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;

import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public @Group class Array<T> extends Collection<T> implements ReadableArray<T>, WriteableArray<T> {
    private final Object[] data;

    public Array(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Negative array size " + count + ".");
        }
        data = new Object[count];
    }

    @SafeVarargs
    public Array(T... items) {
        this(items.length);
        for (int i = 0; i < items.length; i++) {
            data[i] = items[i];
        }
    }

    public Array(@Mandatory Iterable<? extends T> iterable) {
        this(count(iterable));
        int i = 0;
        for (T item : iterable) {
            data[i] = item;
            i++;
        }
    }

    public Object[] getData() {
        return data;
    }

    @Override
    public int count() {
        return data.length;
    }

    public void wipe() {
        Arrays.fill(data, null);
    }

    @Override
    public T get(int i) {
        return (T) data[i];
    }

    @Override
    public void set(int i, T item) {
        data[i] = item;
    }

    @Override
    public @Mandatory Iterator<T> iterator() {
        return (Iterator<T>) Arrays.stream(data).iterator();
    }

    private static <T> int count(@Mandatory Iterable<? extends T> iterable) {
        int count = 0;
        for (T ignored : iterable) {
            count++;
        }
        return count;
    }
}
