package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;

public @Component class Capacity {
    private final int value;

    public Capacity(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("Capacity must be > 0.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
