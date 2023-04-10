package cz.mg.collections;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.utilities.CompareFunction;
import cz.mg.collections.utilities.CompareFunctions;

public @Data interface ReadableCollection<T> extends Iterable<T> {
    int count();

    default boolean isEmpty() {
        return count() <= 0;
    }

    default boolean contains(@Optional T value) {
        return contains(value, CompareFunctions.EQUALS());
    }

    default boolean contains(@Optional T value, @Mandatory CompareFunction<T> compareFunction) {
        for (T current : this) {
            if (current == value) {
                return true;
            }

            if (current == null || value == null) {
                continue;
            }

            if (compareFunction.equals(current, value)) {
                return true;
            }
        }
        return false;
    }
}
