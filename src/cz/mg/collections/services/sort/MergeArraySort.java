package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.coponents.Direction;
import cz.mg.collections.coponents.OrderFunction;

public @Service class MergeArraySort implements ArraySort {
    private static volatile @Service MergeArraySort instance;

    public static @Service MergeArraySort getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new MergeArraySort();
                }
            }
        }
        return instance;
    }

    private MergeArraySort() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void sort(@Mandatory Array<T> array, @Mandatory OrderFunction<T> f, @Mandatory Direction direction) {
        if (array.count() < 2) {
            return;
        }

        Object[] source = array.getData();
        Object[] target = new Object[array.count()];

        int size = 1;
        while (size < array.count()) {
            for (int i = 0; i < array.count(); i += size * 2) {
                int m1 = Math.min(i + size, array.count());
                int m2 = Math.min(i + size * 2, array.count());
                int i1 = i;
                int i2 = i + size;
                int t = i;
                while (i1 < m1 || i2 < m2) {
                    if (i1 >= m1) {
                        target[t] = source[i2];
                        i2++;
                    } else if (i2 >= m2) {
                        target[t] = source[i1];
                        i1++;
                    } else {
                        T o1 = (T) source[i1];
                        T o2 = (T) source[i2];
                        int order = f.orderOptional(
                            direction == Direction.ASCENDING ? o1 : o2,
                            direction == Direction.ASCENDING ? o2 : o1
                        );
                        if (order < 0) {
                            target[t] = source[i1];
                            i1++;
                        } else {
                            target[t] = source[i2];
                            i2++;
                        }
                    }
                    t++;
                }
            }

            Object[] temp = source;
            source = target;
            target = temp;

            size *= 2;
        }

        if (source != array.getData()) {
            for (int i = 0; i < array.count(); i++) {
                target[i] = source[i];
            }
        }
    }
}
