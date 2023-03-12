package cz.mg.collections.set;

import cz.mg.annotations.classes.Group;

@Group class SetItem<T> {
    private T value;
    private int index;

    SetItem(T value, int index) {
        this.value = value;
        this.index = index;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
