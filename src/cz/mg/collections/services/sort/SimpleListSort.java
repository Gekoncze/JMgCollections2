package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.utilities.Direction;
import cz.mg.collections.utilities.OrderFunction;

public @Service class SimpleListSort implements ListSort {
    private static volatile @Service SimpleListSort instance;

    public static @Service SimpleListSort getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SimpleListSort();
                }
            }
        }
        return instance;
    }

    private SimpleListSort() {
    }

    @Override
    public <T> void sort(@Mandatory List<T> list, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        boolean sorted = false;
        while (!sorted) {
            sorted = step(list, f, direction);
        }
    }

    public <T> boolean step(@Mandatory List<T> list, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        if (list.count() < 2) {
            return true;
        }

        boolean swapped = false;
        for (ListItem<T> item = list.getFirstItem(); item != null; item = item.getNextItem()) {
            if (item.getNextItem() != null) {
                swapped |= swap(item, item.getNextItem(), f, direction);
            }
        }

        return !swapped;
    }

    private <T> boolean swap(
        @Mandatory ListItem<T> i1,
        @Mandatory ListItem<T> i2,
        @Mandatory OrderFunction<T> f,
        @Mandatory Direction direction
    ) {
        @Optional T o1 = i1.get();
        @Optional T o2 = i2.get();

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

        i1.set(o2);
        i2.set(o1);
        return true;
    }
}
