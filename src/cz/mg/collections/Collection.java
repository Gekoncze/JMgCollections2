package cz.mg.collections;

import cz.mg.annotations.classes.Data;

public abstract @Data class Collection<T> implements ReadableCollection<T>, WriteableCollection<T> {
    /**
     * This method is only used for easier debugging in IDEs that do not support displaying
     * content of iterables during debugging.
     */
    protected Object[] debug() {
        int count = 0;
        for (Object ignored : this) {
            count++;
        }

        Object[] array = new Object[count];
        int i = 0;
        for (Object o : this) {
            array[i] = o;
            i++;
        }

        return array;
    }
}
