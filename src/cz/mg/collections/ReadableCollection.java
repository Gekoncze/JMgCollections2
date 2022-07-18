package cz.mg.collections;

import cz.mg.annotations.classes.Group;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.utilities.CompareFunction;
import cz.mg.collections.utilities.CompareFunctions;

public @Group interface ReadableCollection<T> extends Iterable<T> {
    int count();

    default boolean isEmpty() {
        return count() <= 0;
    }

    default boolean contains(@Optional T wanted) {
        return contains(wanted, CompareFunctions.EQUALS);
    }

    default boolean contains(@Optional T wanted, @Mandatory CompareFunction compareFunction) {
        for (T current : this) {
            if (compareFunction.equals(current, wanted)) {
                return true;
            }
        }
        return false;
    }
}
