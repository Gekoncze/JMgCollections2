package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Component interface EqualsFunction<T> {
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
