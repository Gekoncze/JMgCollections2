package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public @Storage class List<T> extends Collection<T> implements ReadableList<T>, WriteableList<T> {
    private int count;
    private ListItem<T> firstItem;
    private ListItem<T> lastItem;

    public List() {
    }

    @SafeVarargs
    public List(T... array) {
        for(T item : array) {
            addLast(item);
        }
    }

    public List(@Mandatory Iterable<? extends T> iterable) {
        for(T item : iterable) {
            addLast(item);
        }
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public ListItem<T> getFirstItem() {
        return firstItem;
    }

    @Override
    public ListItem<T> getLastItem() {
        return lastItem;
    }

    @Override
    public T getFirst() {
        if(firstItem != null) {
            return firstItem.get();
        } else {
            throw new ArrayIndexOutOfBoundsException("Missing first item.");
        }
    }

    @Override
    public T getLast() {
        if(lastItem != null) {
            return lastItem.get();
        } else {
            throw new ArrayIndexOutOfBoundsException("Missing last item.");
        }
    }

    @Override
    public T get(int i) {
        return getItem(i).get();
    }

    @Override
    public void setFirst(T data) {
        if(firstItem != null) {
            firstItem.set(data);
        } else {
            throw new ArrayIndexOutOfBoundsException("Missing first item.");
        }
    }

    @Override
    public void setLast(T data) {
        if(lastItem != null) {
            lastItem.set(data);
        } else {
            throw new ArrayIndexOutOfBoundsException("Missing last item.");
        }
    }

    @Override
    public void set(int i, T data) {
        getItem(i).set(data);
    }

    @Override
    public void addFirst(T data) {
        if(firstItem == null) {
            firstItem = new ListItem<>(this, data);
            lastItem = firstItem;
        } else {
            ListItem<T> nextItem = firstItem;
            firstItem = new ListItem<>(this, data);
            firstItem.setNextItem(nextItem);
            nextItem.setPreviousItem(firstItem);
        }

        count++;
    }

    @Override
    public void addLast(T data) {
        if(lastItem == null) {
            lastItem = new ListItem<>(this, data);
            firstItem = lastItem;
        } else {
            ListItem<T> previousItem = lastItem;
            lastItem = new ListItem<>(this, data);
            lastItem.setPreviousItem(previousItem);
            previousItem.setNextItem(lastItem);
        }

        count++;
    }

    @Override
    public void add(int i, T data) {
        if(i == 0) {
            addFirst(data);
        } else if(i == count) {
            addLast(data);
        } else {
            ListItem<T> currentItem = new ListItem<>(this, data);
            ListItem<T> nextItem = getItem(i);
            ListItem<T> previousItem = nextItem.getPreviousItem();

            if(previousItem == null) {
                throw new NullPointerException();
            }

            currentItem.setNextItem(nextItem);
            nextItem.setPreviousItem(currentItem);

            currentItem.setPreviousItem(previousItem);
            previousItem.setNextItem(currentItem);

            count++;
        }
    }

    @Override
    public void addCollectionLast(@Mandatory Iterable<T> collection) {
        for (T object : collection) {
            addLast(object);
        }
    }

    @Override
    public T removeFirst() {
        if(firstItem == null) {
            throw new ArrayIndexOutOfBoundsException("Missing first item.");
        }

        ListItem<T> item = firstItem;
        ListItem<T> nextItem = item.getNextItem();

        firstItem = nextItem;
        item.setNextItem(null);

        if(nextItem != null) {
            nextItem.setPreviousItem(null);
        } else {
            lastItem = null;
        }

        count--;

        return item.get();
    }

    @Override
    public T removeLast() {
        if(lastItem == null) {
            throw new ArrayIndexOutOfBoundsException("Missing last item.");
        }

        ListItem<T> item = lastItem;
        ListItem<T> previousItem = item.getPreviousItem();

        lastItem = previousItem;
        item.setPreviousItem(null);

        if(previousItem != null) {
            previousItem.setNextItem(null);
        } else {
            firstItem = null;
        }

        count--;

        return item.get();
    }

    @Override
    public T remove(int i) {
        return remove(getItem(i));
    }

    @Override
    public T remove(@Mandatory ListItem<T> item) {
        if (item.getList() != this) {
            throw new IllegalArgumentException();
        }

        if(item == getFirstItem()) {
            return removeFirst();
        } else if(item == getLastItem()){
            return removeLast();
        } else {
            ListItem<T> nextItem = item.getNextItem();
            ListItem<T> previousItem = item.getPreviousItem();

            item.setNextItem(null);
            item.setPreviousItem(null);
            previousItem.setNextItem(nextItem);
            nextItem.setPreviousItem(previousItem);

            count--;

            return item.get();
        }
    }

    @Override
    public void clear() {
        int count = this.count;
        for(int i = 0; i < count; i++) {
            removeFirst();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private @Optional ListItem<T> current = firstItem;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if(hasNext()) {
                    T result = current.get();
                    current = current.getNextItem();
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    private @Mandatory ListItem<T> getItem(int i) {
        // can be optimized - iterate from last element if i > count / 2

        if(i < 0 || i >= count){
            throw new ArrayIndexOutOfBoundsException(i + " out of " + count);
        }

        ListItem<T> item = firstItem;
        for(int j = 0; j < i; j++) {
            if(item != null) {
                item = item.getNextItem();
            } else {
                throw new NullPointerException();
            }
        }

        if(item != null) {
            return item;
        } else {
            throw new NullPointerException();
        }
    }
}
