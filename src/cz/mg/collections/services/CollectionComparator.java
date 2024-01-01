package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.components.CompareFunction;
import cz.mg.collections.components.CompareFunctions;

import java.util.Iterator;

public @Service class CollectionComparator {
    private static volatile @Service CollectionComparator instance;

    public static @Service CollectionComparator getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new CollectionComparator();
                }
            }
        }
        return instance;
    }

    private CollectionComparator() {
    }

    public <T> boolean equals(
        @Optional ReadableCollection<T> a,
        @Optional ReadableCollection<T> b
    ) {
        return equals(a, b, CompareFunctions.EQUALS());
    }

    public <T> boolean equals(
        @Optional ReadableCollection<T> a,
        @Optional ReadableCollection<T> b,
        @Mandatory CompareFunction<T> compareFunction
    ) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (a.count() != b.count()) {
            return false;
        }

        Iterator<T> ai = a.iterator();
        Iterator<T> bi = b.iterator();
        while (ai.hasNext() && bi.hasNext()) {
            @Optional T aa = ai.next();
            @Optional T bb = bi.next();

            if (!compareFunction.equalsOptional(aa, bb)) {
                return false;
            }
        }

        return true;
    }
}
