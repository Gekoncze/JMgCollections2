package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Component interface OrderFunction<T> {
    int order(@Mandatory T a, @Mandatory T b);

    default int orderOptional(@Optional T a, @Optional T b) {
        if (a == b) {
            return 0;
        }

        if (a == null) {
            return 1;
        }

        if (b == null) {
            return -1;
        }

        return order(a, b);
    }
}
