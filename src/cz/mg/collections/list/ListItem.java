package cz.mg.collections.list;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage class ListItem<T> implements ReadableListItem<T>, WriteableListItem<T> {
    private T data;
    private @Optional ListItem<T> previousItem;
    private @Optional ListItem<T> nextItem;

    protected ListItem() {
    }

    protected ListItem(T data) {
        this.data = data;
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
    public @Optional ListItem<T> getPreviousItem() {
        return previousItem;
    }

    protected void setPreviousItem(@Optional ListItem<T> previousItem) {
        this.previousItem = previousItem;
    }

    @Override
    public @Optional ListItem<T> getNextItem() {
        return nextItem;
    }

    protected void setNextItem(@Optional ListItem<T> nextItem) {
        this.nextItem = nextItem;
    }
}
