package cz.mg.collections.coponents;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Component interface HashFunction<T> {
    int hash(@Mandatory T o);

    default int hashOptional(@Optional T o) {
        if (o == null) {
            return 0;
        }

        return hash(o);
    }
}
