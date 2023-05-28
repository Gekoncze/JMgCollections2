package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.array.Array;
import cz.mg.collections.components.Direction;
import cz.mg.collections.components.OrderFunction;

public @Service class SimpleArraySort implements ArraySort {
    private static volatile @Service SimpleArraySort instance;

    public static @Service SimpleArraySort getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SimpleArraySort();
                }
            }
        }
        return instance;
    }

    private SimpleArraySort() {
    }

    @Override
    public <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        boolean sorted = false;
        while (!sorted) {
            sorted = step(array, f, direction);
        }
    }

    public <T> boolean step(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        if (array.count() < 2) {
            return true;
        }

        boolean swapped = false;
        for (int i = 0; i < array.count() - 1; i++) {
            swapped |= swap(array, i, i + 1, f, direction);
        }

        return !swapped;
    }

    private <T> boolean swap(
        @Mandatory Array<T> array,
        int i1,
        int i2,
        @Mandatory OrderFunction<T> f,
        @Mandatory Direction direction
    ) {
        @Optional T o1 = array.get(i1);
        @Optional T o2 = array.get(i2);

        int order = f.orderOptional(o1, o2);

        if (order == 0) {
            return false;
        }

        if (order < 0 && direction == Direction.ASCENDING) {
            return false;
        }

        if (order > 0 && direction == Direction.DESCENDING) {
            return false;
        }

        array.set(i1, o2);
        array.set(i2, o1);
        return true;
    }
}
