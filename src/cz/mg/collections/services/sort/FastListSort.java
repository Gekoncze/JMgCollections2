package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.utilities.Direction;
import cz.mg.collections.utilities.OrderFunction;

public @Service class FastListSort implements ListSort {
    private static @Optional FastListSort instance;

    public static @Mandatory FastListSort getInstance() {
        if (instance == null) {
            instance = new FastListSort();
            instance.arraySort = FastArraySort.getInstance();
        }
        return instance;
    }

    private ArraySort arraySort;

    private FastListSort() {
    }

    @Override
    public <T> void sort(@Mandatory List<T> list, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        Array<T> array = new Array<>(list.count());
        listToArray(list, array);
        arraySort.sort(array, f, direction);
        arrayToList(list, array);
    }

    private <T> void listToArray(@Mandatory List<T> list, @Mandatory Array<T> array) {
        int i = 0;
        for (T t : list) {
            array.set(i, t);
            i++;
        }
    }

    private <T> void arrayToList(List<T> list, Array<T> array) {
        int i = 0;
        for (ListItem<T> item = list.getFirstItem(); item != null; item = item.getNextItem()) {
            item.set(array.get(i));
            i++;
        }
    }
}