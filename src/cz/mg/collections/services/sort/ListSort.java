package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.coponents.Direction;
import cz.mg.collections.coponents.OrderFunction;

public @Service interface ListSort {
    <T> void sort(@Mandatory List<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction);
}
