package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.components.Direction;
import cz.mg.collections.components.OrderFunction;

import java.util.Arrays;

public @Service class FastArraySort implements ArraySort {
    private static volatile @Service FastArraySort instance;

    public static @Service FastArraySort getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FastArraySort();
                }
            }
        }
        return instance;
    }

    private FastArraySort() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        if (direction == Direction.ASCENDING) {
            Arrays.sort(array.getData(), (o1, o2) -> f.orderOptional((T) o1, (T) o2));
        } else {
            Arrays.sort(array.getData(), (o1, o2) -> f.orderOptional((T) o2, (T) o1));
        }
    }
}
