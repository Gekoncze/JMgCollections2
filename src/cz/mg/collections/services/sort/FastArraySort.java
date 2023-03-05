package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.array.Array;
import cz.mg.collections.utilities.Direction;
import cz.mg.collections.utilities.OrderFunction;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public @Service class FastArraySort implements ArraySort {
    private static @Optional FastArraySort instance;

    public static @Mandatory FastArraySort getInstance() {
        if (instance == null) {
            instance = new FastArraySort();
        }
        return instance;
    }

    private FastArraySort() {
    }

    @Override
    public <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        if (direction == Direction.ASCENDING) {
            Arrays.sort(array.getData(), (o1, o2) -> f.orderOptional((T) o1, (T) o2));
        } else {
            Arrays.sort(array.getData(), (o1, o2) -> f.orderOptional((T) o2, (T) o1));
        }
    }
}
