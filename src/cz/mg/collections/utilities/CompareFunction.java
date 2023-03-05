package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Utility interface CompareFunction<T> {
    boolean equals(@Mandatory T a, @Mandatory T b);

    default boolean equalsOptional(@Optional T a, @Optional T b) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        return equals(a, b);
    }
}
