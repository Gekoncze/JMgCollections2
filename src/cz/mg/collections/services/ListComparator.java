package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.collections.utilities.CompareFunction;

import java.util.Iterator;

public @Service class ListComparator {
    private static @Optional ListComparator instance;

    public static @Mandatory ListComparator getInstance() {
        if (instance == null) {
            instance = new ListComparator();
        }
        return instance;
    }

    private ListComparator() {
    }

    public <T> boolean equals(List<T> a, List<T> b, CompareFunction compareFunction) {
        if (a.count() != b.count()) {
            return false;
        }

        Iterator<T> ai = a.iterator();
        Iterator<T> bi = b.iterator();
        while (ai.hasNext() && bi.hasNext()) {
            T aa = ai.next();
            T bb = bi.next();
            if (!compareFunction.equals(aa, bb)) {
                return false;
            }
        }

        return true;
    }
}
