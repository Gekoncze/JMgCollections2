package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.utilities.OrderFunction;

@SuppressWarnings({"rawtypes", "unchecked"})
public @Service class ListSorter {
    private static @Optional ListSorter instance;

    public static @Mandatory ListSorter getInstance() {
        if (instance == null) {
            instance = new ListSorter();
            instance.arraySorter = ArraySorter.getInstance();
        }
        return instance;
    }

    private ArraySorter arraySorter;

    private ListSorter() {
    }

    public <T> void sort(@Mandatory List<T> list, @Mandatory OrderFunction orderFunction) {
        Array array = new Array(Object.class, list);
        arraySorter.sort(array, orderFunction);

        int i = 0;
        for (ListItem<T> listItem = list.getFirstItem(); listItem != null; listItem = listItem.getNextItem()) {
            listItem.set((T) array.get(i));
            i++;
        }
    }

    public <T> @Mandatory List<T> sortCopy(@Mandatory List<T> list, @Mandatory OrderFunction orderFunction) {
        List<T> copy = new List<>(list);
        sort(copy, orderFunction);
        return copy;
    }
}
