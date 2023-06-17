package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;

public @Component class Capacity {
    private final int value;

    public Capacity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
