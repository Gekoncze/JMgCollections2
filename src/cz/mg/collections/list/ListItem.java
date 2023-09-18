package cz.mg.collections.list;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;

import java.util.NoSuchElementException;

public @Data class ListItem<T> implements ReadableListItem<T>, WriteableListItem<T> {
    private final @Mandatory List<T> list;
    private T data;
    private ListItem<T> previousItem;
    private ListItem<T> nextItem;

    protected ListItem(@Mandatory List<T> list, T data) {
        this.list = list;
        this.data = data;
    }

    @Override
    public @Mandatory List<T> getList() {
        return list;
    }

    @Override
    public T get() {
        return data;
    }

    @Override
    public void set(T data) {
        this.data = data;
    }

    @Override
    public ListItem<T> getPreviousItem() {
        return previousItem;
    }

    protected void setPreviousItem(ListItem<T> previousItem) {
        this.previousItem = previousItem;
    }

    @Override
    public ListItem<T> getNextItem() {
        return nextItem;
    }

    protected void setNextItem(ListItem<T> nextItem) {
        this.nextItem = nextItem;
    }

    @Override
    public T removePrevious() {
        if (previousItem != null) {
            return list.removeItem(previousItem);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T removeNext() {
        if (nextItem != null) {
            return list.removeItem(nextItem);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T remove() {
        return list.removeItem(this);
    }
}
