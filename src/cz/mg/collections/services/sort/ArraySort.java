package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.functions.OrderFunction;

public @Service interface ArraySort {
    <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction);
}
