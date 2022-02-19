package cz.mg.collections.array;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;

import java.util.Arrays;
import java.util.Iterator;

public @Storage class Array<T> extends Collection<T> implements ReadableArray<T>, WriteableArray<T> {
    private final T[] data;

    public Array(@Mandatory Class<T> clazz, int count) {
        if(count < 0) {
            throw new IllegalArgumentException("Negative array size of " + count + ".");
        }
        //noinspection unchecked
        data = (T[]) java.lang.reflect.Array.newInstance(clazz, count);
    }

    @SafeVarargs
    public Array(@Mandatory Class<T> clazz, T... items) {
        this(clazz, items.length);
        for(int i = 0; i < items.length; i++) {
            data[i] = items[i];
        }
    }

    public Array(@Mandatory Class<T> clazz, @Mandatory Iterable<? extends T> iterable) {
        this(clazz, count(iterable));
        int i = 0;
        for(T item : iterable) {
            data[i] = item;
            i++;
        }
    }

    public T[] getData() {
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
        return data[i];
    }

    @Override
    public void set(int i, T item) {
        data[i] = item;
    }

    @Override
    public @Mandatory Iterator<T> iterator() {
        return Arrays.stream(data).iterator();
    }

    private static <T> int count(@Mandatory Iterable<? extends T> iterable) {
        int count = 0;
        for(T ignored : iterable) {
            count++;
        }
        return count;
    }
}
