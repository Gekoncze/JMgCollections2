package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.components.Direction;
import cz.mg.functions.OrderFunction;

public @Service class MergeListSort implements ListSort {
    private static volatile @Service MergeListSort instance;

    public static @Service MergeListSort getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new MergeListSort();
                    instance.arraySort = MergeArraySort.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ArraySort arraySort;

    private MergeListSort() {
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
