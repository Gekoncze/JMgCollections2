package cz.mg.collections;

import cz.mg.annotations.classes.Storage;
import cz.mg.annotations.requirement.Optional;

public @Storage
interface ReadableCollection<T> extends Iterable<T> {
    int count();

    default boolean isEmpty() {
        return count() <= 0;
    }

    default boolean contains(@Optional T wanted) {
        for (T item : this) {
            if (item == null) {
                if (wanted == null) {
                    return true;
                }
            } else {
                if (item.equals(wanted)) {
                    return true;
                }
            }
        }
        return false;
    }
}
