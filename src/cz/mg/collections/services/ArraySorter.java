package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.array.Array;
import cz.mg.collections.utilities.OrderFunction;

import java.util.Arrays;

public @Service class ArraySorter {
    private static @Optional ArraySorter instance;

    public static @Mandatory ArraySorter getInstance() {
        if (instance == null) {
            instance = new ArraySorter();
        }
        return instance;
    }

    private ArraySorter() {
    }

    public <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> orderFunction) {
        Arrays.sort(array.getData(), orderFunction::order);
    }

    public <T> @Mandatory Array<T> sortCopy(@Mandatory Array<T> array, @Mandatory OrderFunction<T> orderFunction) {
        Array<T> copy = new Array<>(array.getClazz(), array);
        sort(copy, orderFunction);
        return copy;
    }
}
